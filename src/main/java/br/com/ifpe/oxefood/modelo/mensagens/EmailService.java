package br.com.ifpe.oxefood.modelo.mensagens;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Value("${spring.mail.username}") private String username;
    @Value("${spring.mail.password}") private String password;
    @Value("${spring.mail.host}") private String host;
    @Value("${spring.mail.port}") private int port;

    @Async
    public void enviarEmailConfirmacaoCadastroCliente(String email, String nomeCliente) {
        
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("cliente", new ClienteEmailDTO(nomeCliente));

        String conteudo = templateEngine.process("bem_vindo_cliente", context);

        try {
            JavaMailSender mailSender = getJavaMailSender();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom("seu-email@ifpe.edu.br");
            helper.setTo(email);
            helper.setSubject("Bem vindo ao nosso aplicativo!");
            helper.setText(conteudo, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
    
    // DTO auxiliar para o template não quebrar
    private record ClienteEmailDTO(String nome) {}
}