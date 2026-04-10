package br.com.ifpe.oxefood.modelo.carro;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.util.exception.EntidadeNaoEncontradaException;
import jakarta.transaction.Transactional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository repository;

    @Transactional
    public Carro save(Carro carro) {
        if (carro.getAno() < 2005) {
            throw new RuntimeException("Não é permitido cadastrar carros fabricados antes de 2005.");
        }
        
        carro.setHabilitado(Boolean.TRUE);
        return repository.save(carro);
    }

    public List<Carro> listarTodos() {
        return repository.findAll();
    }

    public Carro obterPorID(Long id) {
        Optional<Carro> consulta = repository.findById(id);
        if (consulta.isPresent()) {
            return consulta.get();
        } else {
            throw new EntidadeNaoEncontradaException("Carro", id);
        }
    }

    @Transactional
    public void update(Long id, Carro carroAlterado) {
        Carro carro = this.obterPorID(id);
        
        carro.setModelo(carroAlterado.getModelo());
        carro.setPlaca(carroAlterado.getPlaca());
        carro.setAno(carroAlterado.getAno());
        carro.setValorDiaria(carroAlterado.getValorDiaria());
        
        repository.save(carro);
    }

    @Transactional
    public void delete(Long id) {
        Carro carro = this.obterPorID(id);
        carro.setHabilitado(Boolean.FALSE);
        repository.save(carro);
    }
}