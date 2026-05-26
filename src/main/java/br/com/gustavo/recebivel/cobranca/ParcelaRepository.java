package br.com.gustavo.recebivel.cobranca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    List<Parcela> findByCobrancaId(Long cobrancaId);

    List<Parcela> findByStatusAndDataVencimentoBefore(StatusParcela status, LocalDate data);
}