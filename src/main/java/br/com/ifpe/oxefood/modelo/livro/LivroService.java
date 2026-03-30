package br.com.ifpe.oxefood.modelo.livro;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.util.exception.EntidadeNaoEncontradaException;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Transactional
    public Livro save(Livro livro) {
        livro.setHabilitado(Boolean.TRUE);
        return repository.save(livro);
    }

    public List<Livro> listarTodos() {
        return repository.findAll();
    }

    public Livro obterPorID(Long id) {
        // Validação de Regra de Negócio: ID não encontrado 
        Optional<Livro> consulta = repository.findById(id);
        if (consulta.isPresent()) { 
            return consulta.get(); 
        } else { 
            throw new EntidadeNaoEncontradaException("Livro", id);
        }
    }

    @Transactional
    public void update(Long id, Livro livroAlterado) {
        // Utilizamos o nosso próprio método que já possui a validação de ID
        Livro livro = this.obterPorID(id); 

        livro.setTitulo(livroAlterado.getTitulo());
        livro.setAutor(livroAlterado.getAutor());
        livro.setValor(livroAlterado.getValor());

        repository.save(livro);
    }

    @Transactional
    public void delete(Long id) {
        Livro livro = this.obterPorID(id);
        livro.setHabilitado(Boolean.FALSE);
        repository.save(livro);
    }
}