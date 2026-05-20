package br.com.ifpe.oxefood.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.modelo.acesso.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        String emailTeste = "admin@oxefood.com";

        // Verifica se o utilizador de teste já existe para não duplicar dados
        if (usuarioRepository.findByUsername(emailTeste).isEmpty()) {
            
            Usuario usuario = Usuario.builder()
                .username(emailTeste)
                // Criptografa a senha "123456" antes de salvar no banco
                .password(passwordEncoder.encode("123456")) 
                .roles(new ArrayList<>())
                .build();
                
            usuario.setHabilitado(Boolean.TRUE);
            usuarioRepository.save(usuario);
            
            System.out.println("=================================================");
            System.out.println(">>> UTILIZADOR DE TESTE CRIADO COM SUCESSO!");
            System.out.println(">>> Email: admin@oxefood.com");
            System.out.println(">>> Senha: 123456");
            System.out.println("=================================================");
        }
    }
}