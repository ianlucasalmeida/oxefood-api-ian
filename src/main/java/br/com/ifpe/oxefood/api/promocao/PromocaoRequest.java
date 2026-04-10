package br.com.ifpe.oxefood.api.promocao;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat; // <-- NOVO IMPORT ADICIONADO AQUI

import br.com.ifpe.oxefood.modelo.promocao.Promocao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromocaoRequest {

    private String titulo;

    // <-- ANOTAÇÃO ADICIONADA PARA ENSINAR O PADRÃO DE DATA AO JAVA
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    // <-- ANOTAÇÃO ADICIONADA PARA ENSINAR O PADRÃO DE DATA AO JAVA
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;

    private String regra;

    private Double valorDesconto;

    private Boolean promoValida;

    // Método responsável por converter o Request (DTO) em uma Entidade Produto
    public Promocao build() {

        return Promocao.builder()
            .titulo(titulo)
            .dataFim(dataFim)
            .dataInicio(dataInicio)
            .regra(regra)
            .valorDesconto(valorDesconto)
            .promoValida(promoValida)
            .build();
    }
}