package br.com.gustavo.recebivel.cobranca;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class ParcelaResponse {

    private Long id;
    private Integer numero;
    private StatusParcela status;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;

   public ParcelaResponse(Parcela parcela){
       this.id = parcela.getId();
       this.numero = parcela.getNumero();
       this.status = parcela.getStatus();
       this.dataVencimento = parcela.getDataVencimento();
       this.dataPagamento = parcela.getDataPagamento();
       this.valor = parcela.getValor();

   }
}
