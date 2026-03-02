package br.com.ifpe.oxefood.modelo.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto save(Produto produto) {

        // Como a nossa entidade Produto herda de EntidadeAuditavel/EntidadeNegocio, 
        // precisamos garantir que ela seja salva como "habilitada" por padrão.
        produto.setHabilitado(Boolean.TRUE);

        return repository.save(produto);
    }
}