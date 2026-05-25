package br.com.gustavo.recebivel.cobranca;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    private BigDecimal valor;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    private StatusParcela status = StatusParcela.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "cobranca_id")
    private Cobranca cobranca;
}