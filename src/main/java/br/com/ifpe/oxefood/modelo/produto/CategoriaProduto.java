package br.com.ifpe.oxefood.modelo.produto;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CategoriaProduto")
@SQLRestriction("habilitado = true")
public class CategoriaProduto extends EntidadeAuditavel {

    @Column(nullable = false, length = 100)
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}