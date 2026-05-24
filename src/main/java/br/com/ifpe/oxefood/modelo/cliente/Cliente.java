package br.com.ifpe.oxefood.modelo.cliente;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel  {
  
   @Column(nullable = false, length = 100)
   private String nome;

   // ATRIBUTO ADICIONADO: O Lombok agora vai gerar o getEmail e setEmail corretos
   @Column
   private String email;

   @Column
   private LocalDate dataNascimento;

   @Column(unique = true)
   private String cpf;

   @Column
   private String foneCelular;

   @Column
   private String foneFixo;

   // Amarração 1:N - Um Cliente para Muitos Endereços
   @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<EnderecoCliente> enderecos;

}