package br.com.gustavo.recebivel.usuario;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Login e geração de token JWT.")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken autenticacao =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());

        authenticationManager.authenticate(autenticacao);

        String token = jwtService.gerarToken(request.getEmail());

        return new LoginResponse(token);
    }
}