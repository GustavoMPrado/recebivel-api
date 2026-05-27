package br.com.gustavo.recebivel.cobranca;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CobrancaResponse {

    private Long id;
    private String descricao;
    private BigDecimal valorTotal;
    private LocalDate dataEmissao;
    private LocalDate dataVencimento;
    private StatusCobranca status;
    private Long clienteId;
    private String clienteNome;

    public CobrancaResponse(Cobranca cobranca){
        this.id = cobranca.getId();
        this.descricao = cobranca.getDescricao();
        this.valorTotal = cobranca.getValorTotal();
        this.dataEmissao = cobranca.getDataEmissao();
        this.dataVencimento = cobranca.getDataVencimento();
        this.status = cobranca.getStatus();
        this.clienteId = cobranca.getCliente().getId();
        this.clienteNome = cobranca.getCliente().getNome();
    }
}
