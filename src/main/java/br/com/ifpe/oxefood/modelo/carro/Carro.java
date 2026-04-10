package br.com.ifpe.oxefood.modelo.carro;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Carro")
@SQLRestriction("habilitado = true")
public class Carro extends EntidadeAuditavel {

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(unique = true, nullable = false, length = 8)
    private String placa;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Double valorDiaria;

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(Double valorDiaria) { this.valorDiaria = valorDiaria; }
}