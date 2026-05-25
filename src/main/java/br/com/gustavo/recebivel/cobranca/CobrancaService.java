package br.com.gustavo.recebivel.cobranca;

import br.com.gustavo.recebivel.cliente.Cliente;
import br.com.gustavo.recebivel.cliente.ClienteService;
import br.com.gustavo.recebivel.erro.RecursoNaoEncontradoException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public Cobranca salvar(CriarCobrancaRequest request) {
        Cliente cliente = clienteService.buscarPorId(request.getClienteId());

        Cobranca cobranca = new Cobranca();
        cobranca.setDescricao(request.getDescricao());
        cobranca.setValorTotal(request.getValorTotal());
        cobranca.setDataEmissao(request.getDataEmissao());
        cobranca.setDataVencimento(request.getDataVencimento());
        cobranca.setCliente(cliente);

        Cobranca cobrancaSalva = cobrancaRepository.save(cobranca);

        BigDecimal valorParcela = request.getValorTotal()
                .divide(BigDecimal.valueOf(request.getQuantidadeParcelas()), 2, RoundingMode.HALF_UP);

        BigDecimal valorAcumulado = BigDecimal.ZERO;

        for (int numero = 1; numero <= request.getQuantidadeParcelas(); numero++) {
            Parcela parcela = new Parcela();
            parcela.setNumero(numero);

            if (numero == request.getQuantidadeParcelas()) {
                parcela.setValor(request.getValorTotal().subtract(valorAcumulado));
            } else {
                parcela.setValor(valorParcela);
                valorAcumulado = valorAcumulado.add(valorParcela);
            }

            parcela.setDataVencimento(request.getDataVencimento().plusMonths(numero - 1));
            parcela.setStatus(StatusParcela.PENDENTE);
            parcela.setCobranca(cobrancaSalva);

            parcelaRepository.save(parcela);
        }

        return cobrancaSalva;
    }

    public Cobranca buscarPorId(Long id) {
        return cobrancaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cobranca nao encontrada"));
    }

}
