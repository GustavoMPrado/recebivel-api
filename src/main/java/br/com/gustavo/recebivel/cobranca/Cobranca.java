package br.com.gustavo.recebivel.cobranca;

import br.com.gustavo.recebivel.cliente.Cliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Cobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private BigDecimal valorTotal;

    private LocalDate dataEmissao;

    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    private StatusCobranca status = StatusCobranca.ABERTA;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
