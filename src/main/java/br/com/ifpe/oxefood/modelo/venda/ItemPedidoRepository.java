package br.com.ifpe.oxefood.modelo.venda;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    // Método útil para listar todos os itens de um determinado pedido
    List<ItemPedido> findByPedidoId(Long idPedido);
}