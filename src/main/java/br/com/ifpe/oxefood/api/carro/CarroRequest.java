package br.com.ifpe.oxefood.api.carro;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.oxefood.modelo.carro.Carro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarroRequest {

    @NotBlank(message = "O modelo é de preenchimento obrigatório")
    @Length(max = 100, message = "O modelo deve ter no máximo 100 caracteres")
    private String modelo;

    @NotBlank(message = "A placa é de preenchimento obrigatório")
    @Length(min = 7, max = 8, message = "A placa deve ter entre 7 e 8 caracteres")
    private String placa;

    @NotNull(message = "O ano é de preenchimento obrigatório")
    private Integer ano;

    @NotNull(message = "O valor da diária é de preenchimento obrigatório")
    private Double valorDiaria;

    public Carro build() {
        Carro carro = new Carro();
        carro.setModelo(modelo);
        carro.setPlaca(placa);
        carro.setAno(ano);
        carro.setValorDiaria(valorDiaria);
        return carro;
    }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(Double valorDiaria) { this.valorDiaria = valorDiaria; }
}