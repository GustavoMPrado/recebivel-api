package br.com.gustavo.recebivel.cobranca;


import br.com.gustavo.recebivel.cliente.Cliente;
import br.com.gustavo.recebivel.cliente.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CobrancaService {

    private final CobrancaRepository cobrancaRepository;
    private final ParcelaRepository parcelaRepository;
    private final ClienteService clienteService;

    public CobrancaService(CobrancaRepository cobrancaRepository, ParcelaRepository parcelaRepository,
                           ClienteService clienteService) {
        this.cobrancaRepository = cobrancaRepository;
        this.parcelaRepository = parcelaRepository;
        this.clienteService = clienteService;
    }

    public List<Cobranca> listar() {
        return cobrancaRepository.findAll();
    }

    public Cobranca salvar(Cobranca cobranca) {
        Long clienteId = cobranca.getCliente().getId();

        Cliente cliente = clienteService.buscarPorId(clienteId);

        cobranca.setCliente(cliente);

        Cobranca cobrancaSalva = cobrancaRepository.save(cobranca);

        Parcela parcela = new Parcela();
        parcela.setNumero(1);
        parcela.setValor(cobrancaSalva.getValorTotal());
        parcela.setDataVencimento(cobrancaSalva.getDataVencimento());
        parcela.setStatus(StatusParcela.PENDENTE);
        parcela.setCobranca(cobrancaSalva);

        parcelaRepository.save(parcela);

        return cobrancaSalva;
    }
}
