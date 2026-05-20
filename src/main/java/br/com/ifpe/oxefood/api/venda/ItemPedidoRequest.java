package br.com.ifpe.oxefood.api.venda;

import br.com.ifpe.oxefood.modelo.venda.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoRequest {

    private Long idPedido;
    
    private Long idProduto;

    private Integer quantidade;

    public ItemPedido build() {
        return ItemPedido.builder()
            .quantidade(quantidade)
            .build();
    }
}