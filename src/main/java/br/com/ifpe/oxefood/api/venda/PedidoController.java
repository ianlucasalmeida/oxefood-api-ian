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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.venda.Pedido;
import br.com.ifpe.oxefood.modelo.venda.PedidoService;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> salvar(@RequestBody PedidoRequest request) {
        // Envia o objeto e os IDs separados para o Service
        Pedido pedido = pedidoService.salvar(request.build(), request.getIdCliente(), request.getIdEntregador());
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido obterPorID(@PathVariable Long id) {
        return pedidoService.obterPorID(id);
    }

    // Método focado apenas em mudar o status logístico do pedido
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody String novoStatus) {
        pedidoService.mudarStatus(id, novoStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}