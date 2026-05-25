package br.com.gustavo.recebivel.cobranca;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CriarCobrancaRequest {

    private String descricao;

    private BigDecimal valorTotal;

    private LocalDate dataEmissao;

    private LocalDate dataVencimento;

    private Integer quantidadeParcelas;

    private Long clienteId;
}
