package br.com.ifpe.oxefood.modelo.entregador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository repository;

    @Transactional
    public Entregador save(Entregador entregador) {

        // Como a entidade herda de EntidadeNegocio, precisamos setar o habilitado como true antes de salvar
        entregador.setHabilitado(Boolean.TRUE);

        return repository.save(entregador);
    }
}