package br.com.gustavo.recebivel.cobranca;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CobrancaService {

    private final CobrancaRepository cobrancaRepository;

    public CobrancaService(CobrancaRepository cobrancaRepository) {
        this.cobrancaRepository = cobrancaRepository;
    }

    public List<Cobranca> listar() {
        return cobrancaRepository.findAll();
    }

    public Cobranca salvar(Cobranca cobranca) {
        return cobrancaRepository.save(cobranca);
    }
}
