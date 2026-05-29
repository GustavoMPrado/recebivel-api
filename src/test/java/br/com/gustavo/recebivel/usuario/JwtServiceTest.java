package br.com.gustavo.recebivel.usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void preparar() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "chaveSecreta",
                "recebivel-api-chave-secreta-local-para-desenvolvimento"
        );

        ReflectionTestUtils.setField(jwtService, "tempoExpiracao", 86400000L);
    }

    @Test
    void deveGerarTokenEExtrairEmail() {
        String token = jwtService.gerarToken("admin@recebivel.com");

        String email = jwtService.extrairEmail(token);

        assertEquals("admin@recebivel.com", email);
    }

    @Test
    void deveValidarTokenCorreto() {
        String token = jwtService.gerarToken("admin@recebivel.com");

        boolean tokenValido = jwtService.tokenValido(token);

        assertTrue(tokenValido);
    }
}