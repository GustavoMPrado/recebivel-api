package br.com.gustavo.recebivel.cobranca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {

    List<Cobranca> findByStatus(StatusCobranca status);

    List<Cobranca> findByClienteId(Long clienteId);
}
