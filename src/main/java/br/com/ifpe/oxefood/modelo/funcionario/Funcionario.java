package br.com.ifpe.oxefood.modelo.funcionario;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Funcionario")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario extends EntidadeAuditavel {

    @OneToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoFuncionario tipo;

    @Column
    private String nome;
    
    @Column(unique = true)
    private String cpf;

}