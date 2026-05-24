package br.com.ifpe.oxefood.modelo.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.modelo.mensagens.EmailService;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Cliente save(Cliente cliente, Usuario usuarioLogado) { 

        cliente.setHabilitado(Boolean.TRUE);
        cliente.setVersao(1L);
        cliente.setDataCriacao(LocalDate.now());
        cliente.setCriadoPor(usuarioLogado); 

        // --- Lógica do Relacionamento 1:N ---
        if (cliente.getEnderecos() != null) {
            for (EnderecoCliente endereco : cliente.getEnderecos()) {
                endereco.setCliente(cliente);
                endereco.setHabilitado(Boolean.TRUE);
                endereco.setVersao(1L);
                endereco.setDataCriacao(LocalDate.now());
                endereco.setCriadoPor(usuarioLogado); 
            }
        }

        // Salva o cliente no banco de dados primeiro
        Cliente clienteSalvo = repository.save(cliente);

        // --- LÓGICA DE ENVIO DE E-MAIL (AULA 26) ---
        // Try-catch adicionado para evitar que o cadastro falhe caso o servidor de e-mail caia
        try {
            emailService.enviarEmailConfirmacaoCadastroCliente(clienteSalvo.getEmail(), clienteSalvo.getNome());
        } catch (Exception e) {
            System.out.println("Aviso: Falha ao enviar o e-mail de boas-vindas. " + e.getMessage());
        }

        return clienteSalvo;
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente obterPorID(Long id) {
        // CORREÇÃO: Utilizar orElse(null) em vez de .get()
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado, Usuario usuarioLogado) { 

        // CORREÇÃO: Utilizar orElse(null) em vez de .get()
        Cliente cliente = repository.findById(id).orElse(null);

        // CORREÇÃO: Validação para evitar NullPointerException
        if (cliente != null) { 
            cliente.setNome(clienteAlterado.getNome());
            cliente.setEmail(clienteAlterado.getEmail()); 
            cliente.setDataNascimento(clienteAlterado.getDataNascimento());
            cliente.setCpf(clienteAlterado.getCpf());
            cliente.setFoneCelular(clienteAlterado.getFoneCelular());
            cliente.setFoneFixo(clienteAlterado.getFoneFixo());

            // CORREÇÃO: Tratamento seguro da lista de endereços
            if (cliente.getEnderecos() != null) {
                cliente.getEnderecos().clear();
            } else {
                cliente.setEnderecos(new ArrayList<>());
            }

            if (clienteAlterado.getEnderecos() != null) {
                for (EnderecoCliente endereco : clienteAlterado.getEnderecos()) {
                    endereco.setCliente(cliente);
                    endereco.setHabilitado(Boolean.TRUE);
                    endereco.setVersao(1L);
                    endereco.setDataCriacao(LocalDate.now());
                    endereco.setCriadoPor(usuarioLogado); 
                    cliente.getEnderecos().add(endereco);
                }
            }

            cliente.setVersao(cliente.getVersao() + 1);
            cliente.setDataUltimaModificacao(LocalDate.now());
            cliente.setUltimaModificacaoPor(usuarioLogado); 

            repository.save(cliente);
        }
    }

    @Transactional
    public void delete(Long id) {

        // CORREÇÃO: Utilizar orElse(null) em vez de .get()
        Cliente cliente = repository.findById(id).orElse(null);

        // CORREÇÃO: Validação para evitar NullPointerException
        if (cliente != null) {
            cliente.setHabilitado(Boolean.FALSE);
            cliente.setVersao(cliente.getVersao() + 1);
            cliente.setDataUltimaModificacao(LocalDate.now());

            repository.save(cliente);
        }
    }
}