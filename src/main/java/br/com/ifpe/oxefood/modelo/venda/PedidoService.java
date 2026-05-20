package br.com.ifpe.oxefood.modelo.venda;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import br.com.ifpe.oxefood.modelo.cliente.ClienteRepository;
import br.com.ifpe.oxefood.modelo.entregador.Entregador;
import br.com.ifpe.oxefood.modelo.entregador.EntregadorRepository;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    // === INJEÇÕES ADICIONADAS PARA AS AMARRAÇÕES ===
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    // O método salvar agora recebe os IDs
    @Transactional
    public Pedido salvar(Pedido pedido, Long idCliente, Long idEntregador) {
        
        // Amarrando o Cliente
        if (idCliente != null) {
            Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
            pedido.setCliente(cliente);
        }

        // Amarrando o Entregador (pode ser nulo no momento inicial do pedido)
        if (idEntregador != null) {
            Entregador entregador = entregadorRepository.findById(idEntregador).orElse(null);
            pedido.setEntregador(entregador);
        }

        pedido.setHabilitado(Boolean.TRUE);
        pedido.setVersao(1L);
        pedido.setDataCriacao(LocalDate.now());
        
        // Regras de negócio do pedido
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatusPedido("PENDENTE");
        pedido.setValorTotal(0.0); 

        return repository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    public Pedido obterPorID(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void mudarStatus(Long id, String novoStatus) {
        Pedido pedido = repository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setStatusPedido(novoStatus);
            pedido.setVersao(pedido.getVersao() + 1);
            pedido.setDataUltimaModificacao(LocalDate.now());
            repository.save(pedido);
        }
    }

    @Transactional
    public void deletar(Long id) {
        Pedido pedido = repository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setHabilitado(Boolean.FALSE);
            pedido.setVersao(pedido.getVersao() + 1);
            pedido.setDataUltimaModificacao(LocalDate.now());
            repository.save(pedido);
        }
    }
}