package br.com.ifpe.oxefood.api.produto;

import br.com.ifpe.oxefood.modelo.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    private String titulo;

    private String descricao;

    private Double valor;

    private Integer tempoEntregaMinimo;

    private Integer tempoEntregaMaximo;

    // === NOVO ATRIBUTO ADICIONADO (AULA C18) ===
    // Recebe o ID da categoria selecionada no Dropdown do React
    private Long idCategoria;

    // Método responsável por converter o Request (DTO) em uma Entidade Produto
    public Produto build() {

        return Produto.builder()
            .titulo(titulo)
            .descricao(descricao)
            .valor(valor)
            .tempoEntregaMinimo(tempoEntregaMinimo)
            .tempoEntregaMaximo(tempoEntregaMaximo)
            .build();
    }
}