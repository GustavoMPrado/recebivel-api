package br.com.gustavo.recebivel.cobranca;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CriarCobrancaRequest {

    private String descricao;

    @NotNull(message = "Valor total é obrigatório.")
    @DecimalMin(value = "0.01", message = "Valor total deve ser maior que zero.")
    private BigDecimal valorTotal;

    @NotNull(message = "Data de emissão é obrigatória.")
    private LocalDate dataEmissao;

    @NotNull(message = "Data de vencimento é obrigatória.")
    private LocalDate dataVencimento;

    @NotNull(message = "Quantidade de parcelas é obrigatória.")
    @Min(value = 1, message = "Quantidade de parcelas deve ser maior que zero.")
    private Integer quantidadeParcelas;

    @NotNull(message = "Cliente é obrigatório.")
    private Long clienteId;
}
