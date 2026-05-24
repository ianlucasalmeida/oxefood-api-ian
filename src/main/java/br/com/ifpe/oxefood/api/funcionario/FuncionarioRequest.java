package br.com.ifpe.oxefood.api.funcionario;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.modelo.funcionario.Funcionario;
import br.com.ifpe.oxefood.modelo.funcionario.TipoFuncionario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioRequest {

    private String nome;
    private String cpf;
    private TipoFuncionario tipo;
    
    // Dados para o login embutido
    private String email;
    private String password;

    public Funcionario build() {
        return Funcionario.builder()
                .usuario(Usuario.builder()
                        .username(email)
                        .password(password)
                        .build())
                .nome(nome)
                .cpf(cpf)
                .tipo(tipo)
                .build();
    }
}