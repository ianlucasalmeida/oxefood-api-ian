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
import io.jsonwebtoken.ExpiredJwtException;
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
        
        // Verifica se o cabeçalho existe e começa com o padrão esperado
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                UserDetails userDetails = this.usuarioService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                    
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities()
                    );
                    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            // Token expirado capturado: garante que não há usuário logado e não lança exceção 500
            SecurityContextHolder.clearContext();
            logger.warn("Token JWT expirado: " + e.getMessage());
        } catch (Exception e) {
            // Captura qualquer outra falha na extração/validação para evitar erro 500
            SecurityContextHolder.clearContext();
            logger.error("Erro ao processar token JWT: " + e.getMessage());
        }
        
        filterChain.doFilter(request, response);
    }
}