package br.com.ifpe.oxefood.modelo.produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // 1. Busca exata pelo código
    @Query(value = "SELECT p FROM Produto p WHERE p.codigo = :codigo")
    List<Produto> consultarPorCodigo(String codigo);

    // 2. Busca aproximada pelo título (ignorando maiúsculas e minúsculas)
    List<Produto> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    // 3. Busca exata pela Categoria
    @Query(value = "SELECT p FROM Produto p WHERE p.categoria.id = :idCategoria")
    List<Produto> consultarPorCategoria(Long idCategoria);

    // 4. Busca combinada (Título + Categoria)
    @Query(value = "SELECT p FROM Produto p WHERE p.titulo ilike %:titulo% AND p.categoria.id = :idCategoria")
    List<Produto> consultarPorTituloECategoria(String titulo, Long idCategoria);
}