package br.com.ifpe.oxefood.modelo.promocao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.util.exception.EntidadeNaoEncontradaException;
import jakarta.transaction.Transactional;

@Service
public class PromocaoService {

    @Autowired
    private PromocaoRepository repository;

    @Transactional
    public Promocao save(Promocao promocao) {
        if (promocao.getValorDesconto() < 50) {
            throw new RuntimeException("Não é permitido cadastrar promoções em valores abaixo de 50.");
        }
        
        // CORREÇÃO 3: Agora ela nasce habilitada para o @SQLRestriction conseguir achar
        promocao.setHabilitado(Boolean.TRUE);
        promocao.setPromoValida(Boolean.TRUE);
        
        return repository.save(promocao);
    }

    public List<Promocao> listarTodos() {
        return repository.findAll();
    }

    public Promocao obterPorID(Long id) {
        Optional<Promocao> consulta = repository.findById(id);
        if (consulta.isPresent()) {
            return consulta.get();
        } else {
            throw new EntidadeNaoEncontradaException("Promocao", id);
        }
    }

    @Transactional
    public void update(Long id, Promocao promocaoAlterada) {
        Promocao promocao = this.obterPorID(id);
        
        promocao.setTitulo(promocaoAlterada.getTitulo());
        promocao.setDataInicio(promocaoAlterada.getDataInicio());
        promocao.setDataFim(promocaoAlterada.getDataFim());
        promocao.setRegra(promocaoAlterada.getRegra());
        promocao.setValorDesconto(promocaoAlterada.getValorDesconto());
        promocao.setPromoValida(promocaoAlterada.getPromoValida());
        
        repository.save(promocao);
    }

    @Transactional
    public void delete(Long id) {
        Promocao promocao = this.obterPorID(id);
        
        // CORREÇÃO 1 e 2: O código sujo foi apagado e substituído pela lógica correta de ocultar o registro (FALSE)
        promocao.setHabilitado(Boolean.FALSE);
        
        repository.save(promocao);
    }
}