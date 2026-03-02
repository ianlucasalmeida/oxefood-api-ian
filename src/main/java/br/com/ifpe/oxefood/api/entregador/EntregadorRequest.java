package br.com.ifpe.oxefood.api.entregador;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.oxefood.modelo.entregador.Entregador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorRequest {

    private String nome;

    private String cpf;

    private String rg;

    // A anotação abaixo garante que o Spring entenda a data vinda da máscara do React (dd/MM/yyyy)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String foneCelular;

    private String foneFixo;

    private Integer qtdEntregasRealizadas;

    private Double valorFrete;

    private String rua;

    private String numero;

    private String bairro;

    private String cidade;

    private String cep;

    private String uf;

    private String complemento;

    private Boolean ativo;

    // Método responsável por converter o Request (DTO) em uma Entidade Entregador
    public Entregador build() {

        return Entregador.builder()
            .nome(nome)
            .cpf(cpf)
            .rg(rg)
            .dataNascimento(dataNascimento)
            .foneCelular(foneCelular)
            .foneFixo(foneFixo)
            .qtdEntregasRealizadas(qtdEntregasRealizadas)
            .valorFrete(valorFrete)
            .rua(rua)
            .numero(numero)
            .bairro(bairro)
            .cidade(cidade)
            .cep(cep)
            .uf(uf)
            .complemento(complemento)
            .ativo(ativo)
            .build();
    }
}