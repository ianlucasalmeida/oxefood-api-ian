package br.com.ifpe.oxefood.modelo.acesso;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuario")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends EntidadeAuditavel implements UserDetails {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // NOVO: Relacionamento com a tabela de Perfis (Roles)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Usuario_Perfil", 
               joinColumns = @JoinColumn(name = "usuario_id"), 
               inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private List<Perfil> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}