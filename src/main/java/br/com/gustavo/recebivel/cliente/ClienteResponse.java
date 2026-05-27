package br.com.gustavo.recebivel.cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponse {

    private Long id;
    private String nome;
    private String documento;
    private String email;
    private String telefone;
    private Boolean ativo;

    public ClienteResponse(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.documento = cliente.getDocumento();
        this.email = cliente.getEmail();
        this.telefone = cliente.getTelefone();
        this.ativo = cliente.getAtivo();
    }
}