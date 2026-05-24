package br.com.ifpe.oxefood.modelo.funcionario;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public Funcionario save(Funcionario funcionario) {

        // Salva o usuário de acesso antes de salvar o funcionário
        usuarioService.save(funcionario.getUsuario());

        funcionario.setHabilitado(Boolean.TRUE);
        funcionario.setVersao(1L);
        funcionario.setDataCriacao(LocalDate.now());

        return repository.save(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    public Funcionario obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Funcionario funcionarioAlterado) {

        Funcionario funcionario = repository.findById(id).get();
        funcionario.setNome(funcionarioAlterado.getNome());
        funcionario.setCpf(funcionarioAlterado.getCpf());
        funcionario.setTipo(funcionarioAlterado.getTipo());

        funcionario.setVersao(funcionario.getVersao() + 1);
        funcionario.setDataUltimaModificacao(LocalDate.now());

        repository.save(funcionario);
    }

    @Transactional
    public void delete(Long id) {

        Funcionario funcionario = repository.findById(id).get();
        funcionario.setHabilitado(Boolean.FALSE);
        funcionario.setVersao(funcionario.getVersao() + 1);
        funcionario.setDataUltimaModificacao(LocalDate.now());

        repository.save(funcionario);
    }
}