package br.com.ifpe.oxefood.modelo.produto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Transactional
    public Produto salvar(Produto produto, Long idCategoria) {
        
        if (idCategoria != null) {
            CategoriaProduto categoria = categoriaProdutoRepository.findById(idCategoria).orElse(null);
            produto.setCategoria(categoria);
        }

        produto.setHabilitado(Boolean.TRUE);
        produto.setVersao(1L);
        produto.setDataCriacao(LocalDate.now());
        return repository.save(produto);
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto obterPorID(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void atualizar(Long id, Produto produtoAlterado, Long idCategoria) {
        
        Produto produto = repository.findById(id).orElse(null);

        if (produto != null) {
            if (idCategoria != null) {
                CategoriaProduto categoria = categoriaProdutoRepository.findById(idCategoria).orElse(null);
                produto.setCategoria(categoria);
            }
            
            produto.setCodigo(produtoAlterado.getCodigo());
            produto.setTitulo(produtoAlterado.getTitulo());
            produto.setDescricao(produtoAlterado.getDescricao());
            produto.setValor(produtoAlterado.getValor());
            produto.setTempoEntregaMinimo(produtoAlterado.getTempoEntregaMinimo());
            produto.setTempoEntregaMaximo(produtoAlterado.getTempoEntregaMaximo());
            
            // ATUALIZA O NOME DA IMAGEM NO BANCO
            produto.setImagem(produtoAlterado.getImagem());

            produto.setVersao(produto.getVersao() + 1);
            produto.setDataUltimaModificacao(LocalDate.now());
            repository.save(produto);
        }
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = repository.findById(id).orElse(null);
        if (produto != null) {
            produto.setHabilitado(Boolean.FALSE);
            produto.setVersao(produto.getVersao() + 1);
            produto.setDataUltimaModificacao(LocalDate.now());
            repository.save(produto);
        }
    }

    public List<Produto> filtrar(String codigo, String titulo, Long idCategoria) {

        List<Produto> listaProdutos = repository.findAll();

        if ((codigo != null && !"".equals(codigo)) &&
            (titulo == null || "".equals(titulo)) &&
            (idCategoria == null)) {
            listaProdutos = repository.consultarPorCodigo(codigo);
        } else if ((codigo == null || "".equals(codigo)) &&
                   (titulo != null && !"".equals(titulo)) &&
                   (idCategoria == null)) {
            listaProdutos = repository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
        } else if ((codigo == null || "".equals(codigo)) &&
                   (titulo == null || "".equals(titulo)) &&
                   (idCategoria != null)) {
            listaProdutos = repository.consultarPorCategoria(idCategoria);
        } else if ((codigo == null || "".equals(codigo)) &&
                   (titulo != null && !"".equals(titulo)) &&
                   (idCategoria != null)) {
            listaProdutos = repository.consultarPorTituloECategoria(titulo, idCategoria);
        }

        return listaProdutos;
    }
}