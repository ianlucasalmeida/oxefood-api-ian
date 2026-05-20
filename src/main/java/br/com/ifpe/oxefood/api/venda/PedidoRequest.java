package br.com.ifpe.oxefood.api.venda;

import br.com.ifpe.oxefood.modelo.venda.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    private Long idCliente;
    
    private Long idEntregador;

    public Pedido build() {
        // Os relacionamentos serão montados lá no Service, usando os IDs
        return Pedido.builder()
            .build();
    }
}