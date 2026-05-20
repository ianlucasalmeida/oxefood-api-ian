package br.com.ifpe.oxefood.api.venda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.venda.ItemPedido;
import br.com.ifpe.oxefood.modelo.venda.ItemPedidoService;

@RestController
@RequestMapping("/api/itempedido")
@CrossOrigin
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @PostMapping
    public ResponseEntity<ItemPedido> salvar(@RequestBody ItemPedidoRequest request) {
        ItemPedido itemPedido = itemPedidoService.salvar(
            request.build(), 
            request.getIdPedido(), 
            request.getIdProduto()
        );
        return new ResponseEntity<>(itemPedido, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ItemPedido> listarTodos() {
        return itemPedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ItemPedido obterPorID(@PathVariable Long id) {
        return itemPedidoService.obterPorID(id);
    }

    // Rota específica para carregar o carrinho de um pedido no Front-end
    @GetMapping("/pedido/{idPedido}")
    public List<ItemPedido> listarPorPedido(@PathVariable Long idPedido) {
        return itemPedidoService.listarPorPedido(idPedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemPedidoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}