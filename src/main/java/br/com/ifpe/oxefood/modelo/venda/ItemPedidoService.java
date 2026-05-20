package br.com.ifpe.oxefood.modelo.venda;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.modelo.produto.Produto;
import br.com.ifpe.oxefood.modelo.produto.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public ItemPedido salvar(ItemPedido itemPedido, Long idPedido, Long idProduto) {
        
        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        Produto produto = produtoRepository.findById(idProduto).orElse(null);

        if (pedido != null && produto != null) {
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            
            // Pega o valor atual do produto e salva no item (garante o histórico do preço)
            itemPedido.setValorUnitario(produto.getValor());

            itemPedido.setHabilitado(Boolean.TRUE);
            itemPedido.setVersao(1L);
            itemPedido.setDataCriacao(LocalDate.now());

            ItemPedido itemSalvo = repository.save(itemPedido);

            // Atualiza o valor total do pedido
            Double valorAtual = pedido.getValorTotal() != null ? pedido.getValorTotal() : 0.0;
            Double valorItem = itemSalvo.getValorUnitario() * itemSalvo.getQuantidade();
            pedido.setValorTotal(valorAtual + valorItem);
            pedidoRepository.save(pedido);

            return itemSalvo;
        }
        
        throw new RuntimeException("Pedido ou Produto não encontrados.");
    }

    public List<ItemPedido> listarTodos() {
        return repository.findAll();
    }

    public ItemPedido obterPorID(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ItemPedido> listarPorPedido(Long idPedido) {
        return repository.findByPedidoId(idPedido);
    }

    @Transactional
    public void deletar(Long id) {
        ItemPedido itemPedido = repository.findById(id).orElse(null);
        if (itemPedido != null) {
            
            // Antes de remover, abate o valor do item no total do pedido
            Pedido pedido = itemPedido.getPedido();
            Double valorItem = itemPedido.getValorUnitario() * itemPedido.getQuantidade();
            pedido.setValorTotal(pedido.getValorTotal() - valorItem);
            pedidoRepository.save(pedido);

            itemPedido.setHabilitado(Boolean.FALSE);
            itemPedido.setVersao(itemPedido.getVersao() + 1);
            itemPedido.setDataUltimaModificacao(LocalDate.now());
            repository.save(itemPedido);
        }
    }
}