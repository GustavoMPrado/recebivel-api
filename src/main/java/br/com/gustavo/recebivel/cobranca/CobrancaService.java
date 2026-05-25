package br.com.gustavo.recebivel.cobranca;

import br.com.gustavo.recebivel.cliente.Cliente;
import br.com.gustavo.recebivel.cliente.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CobrancaService {

    private final CobrancaRepository cobrancaRepository;
    private final ClienteService clienteService;

    public CobrancaService(CobrancaRepository cobrancaRepository, ClienteService clienteService) {
        this.cobrancaRepository = cobrancaRepository;
        this.clienteService = clienteService;
    }

    public List<Cobranca> listar() {
        return cobrancaRepository.findAll();
    }

    public Cobranca salvar(Cobranca cobranca) {
        Long clienteId = cobranca.getCliente().getId();

        Cliente cliente = clienteService.buscarPorId(clienteId);

        cobranca.setCliente(cliente);

        return cobrancaRepository.save(cobranca);
    }
}
