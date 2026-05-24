package br.com.ifpe.oxefood.api.cliente;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import br.com.ifpe.oxefood.modelo.cliente.EnderecoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String cpf;

    private String foneCelular;

    private String foneFixo;

    // Nova lista que virá do Front-end
    private List<EnderecoCliente> enderecos;

    public Cliente build() {
        return Cliente.builder()
                .nome(nome)
                .dataNascimento(dataNascimento)
                .cpf(cpf)
                .foneCelular(foneCelular)
                .foneFixo(foneFixo)
                .enderecos(enderecos) // Adicionamos no Builder
                .build();
    }
}