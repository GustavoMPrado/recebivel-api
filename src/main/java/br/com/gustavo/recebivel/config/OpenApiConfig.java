package br.com.gustavo.recebivel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI recebivelApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recebível API")
                        .description("API para controle interno de cobranças, parcelas, vencimentos e pagamentos.")
                        .version("1.0.0"));
    }
}