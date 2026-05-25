package br.com.gustavo.recebivel.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Documento é obrigatório")
    @Size(max = 20, message = "Documento deve ter no máximo 20 caracteres")
    private String documento;

    @Email(message = "Email inválido")
    private String email;


    private String telefone;
    private Boolean ativo = true;
}
