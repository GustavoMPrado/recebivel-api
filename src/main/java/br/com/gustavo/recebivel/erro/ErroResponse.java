package br.com.gustavo.recebivel.erro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroResponse {

    private String mensagem;

    public ErroResponse(String mensagem) {
        this.mensagem = mensagem;
    }
}
