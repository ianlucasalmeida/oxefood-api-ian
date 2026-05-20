package br.com.ifpe.oxefood.api.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.acesso.JwtService;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        
        // 1. O Spring Security tenta validar se o email e a senha estão corretos
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // 2. Se as credenciais estiverem erradas, o Spring lança uma exceção automaticamente.
        // Se chegar aqui, significa que o login foi um sucesso! Fabricamos o Token:
        String token = jwtService.generateToken(request.getUsername());

        return AuthenticationResponse.builder()
            .token(token)
            .build();
    }
}