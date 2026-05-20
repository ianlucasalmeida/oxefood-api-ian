package br.com.ifpe.oxefood.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ifpe.oxefood.modelo.acesso.JwtService;
import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Se não tiver o cabeçalho de autorização ou não começar com "Bearer ", passa direto para ser bloqueado pelas regras
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrai o token ignorando os 7 primeiros caracteres ("Bearer ")
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // Se encontrou o email no token e o utilizador ainda não está autenticado no contexto atual
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Vai à base de dados buscar o utilizador
            UserDetails userDetails = this.usuarioService.loadUserByUsername(userEmail);

            // Se o token for válido para esse utilizador
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {

                // Cria o passe de entrada oficial do Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Salva o utilizador logado no contexto da requisição
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // Deixa a requisição seguir o seu caminho
        filterChain.doFilter(request, response);
    }
}