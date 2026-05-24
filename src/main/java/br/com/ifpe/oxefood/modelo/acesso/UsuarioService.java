package br.com.ifpe.oxefood.modelo.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtService jwtService; // Injetando o serviço de Token

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    // Método corrigido para salvar o usuário no banco
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    // --- LÓGICA DE AUDITORIA (AULA 23) ---
    // Este método captura a requisição do React, lê o cabeçalho Authorization,
    // extrai o Token JWT, descobre o e-mail e busca o usuário no banco.
    public Usuario obterUsuarioLogado(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove a palavra "Bearer "
            String username = jwtService.extractUsername(token); // Descobre o email que está dentro do Token
            return repository.findByUsername(username).orElse(null);
        }
        
        return null;
    }
}