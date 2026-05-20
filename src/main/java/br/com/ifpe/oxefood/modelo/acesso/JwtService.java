package br.com.ifpe.oxefood.modelo.acesso;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Esta é a senha secreta do seu servidor. 
    // Em um projeto real na empresa, isso ficaria escondido nas variáveis de ambiente.
    private static final String SECRET_KEY = "oxefood-api-chave-secreta-muito-longa-para-seguranca-jwt-token";
    
    // Tempo de validade do Token em milissegundos (Aqui está configurado para 1 hora)
    private static final long EXPIRATION_TIME = 3600000;

    // Transforma a nossa string em uma chave criptografada válida
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Método que FABRICA o crachá (Token) quando o usuário faz login com sucesso
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Nome do dono do crachá (e-mail)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de fabricação
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Data de validade
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assinatura carimbada do servidor
                .compact();
    }

    // Método que LÊ o nome do usuário que está dentro do crachá
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Método que VERIFICA se o crachá é verdadeiro, pertence à pessoa e não está vencido
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    // Método interno para checar a validade
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Método interno que "abre" o JWT para ler o que tem dentro
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}