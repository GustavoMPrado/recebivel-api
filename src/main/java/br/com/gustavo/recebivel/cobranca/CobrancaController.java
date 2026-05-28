package br.com.gustavo.recebivel.cobranca;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cobrancas")
public class CobrancaController {

    private final CobrancaService cobrancaService;

    public CobrancaController(CobrancaService cobrancaService) {
        this.cobrancaService = cobrancaService;
    }

    @GetMapping
    public List<CobrancaResponse> listar() {
        List<Cobranca> cobrancas = cobrancaService.listar();
        List<CobrancaResponse> resposta = new ArrayList<>();

        for (Cobranca cobranca : cobrancas) {
            resposta.add(new CobrancaResponse(cobranca));
        }

        return resposta;
    }

    @GetMapping("/status/{status}")
    public List<CobrancaResponse> listarPorStatus(@PathVariable StatusCobranca status) {
        List<Cobranca> cobrancas = cobrancaService.listarPorStatus(status);
        List<CobrancaResponse> resposta = new ArrayList<>();

        for (Cobranca cobranca : cobrancas) {
            resposta.add(new CobrancaResponse(cobranca));
        }

        return resposta;
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CobrancaResponse> listarPorCliente(@PathVariable Long clienteId) {
        List<Cobranca> cobrancas = cobrancaService.listarPorCliente(clienteId);
        List<CobrancaResponse> resposta = new ArrayList<>();

        for (Cobranca cobranca : cobrancas) {
            resposta.add(new CobrancaResponse(cobranca));
        }

        return resposta;
    }

    @GetMapping("/{id}")
    public CobrancaResponse buscarPorId(@PathVariable Long id) {
        Cobranca cobranca = cobrancaService.buscarPorId(id);
        return new CobrancaResponse(cobranca);
    }

    @GetMapping("/{id}/parcelas")
    public List<ParcelaResponse> listarParcelas(@PathVariable Long id) {
        List<Parcela> parcelas = cobrancaService.listarParcelas(id);
        List<ParcelaResponse> resposta = new ArrayList<>();

        for (Parcela parcela : parcelas) {
            resposta.add(new ParcelaResponse(parcela));
        }

        return resposta;
    }

    @PostMapping
    public CobrancaResponse salvar(@RequestBody @Valid CriarCobrancaRequest request) {
        Cobranca cobranca = cobrancaService.salvar(request);
        return new CobrancaResponse(cobranca);
    }

    @PutMapping("/parcelas/{id}/pagamento")
    public ParcelaResponse registrarPagamento(@PathVariable Long id) {
        Parcela parcela = cobrancaService.registrarPagamento(id);
        return new ParcelaResponse(parcela);
    }

    @PutMapping("/{id}/cancelamento")
    public CobrancaResponse cancelar(@PathVariable Long id) {
        Cobranca cobranca = cobrancaService.cancelar(id);
        return new CobrancaResponse(cobranca);
    }

    @PutMapping("/parcelas/vencidas")
    public List<ParcelaResponse> marcarParcelasVencidas() {
        List<Parcela> parcelas = cobrancaService.marcarParcelasVencidas();
        List<ParcelaResponse> resposta = new ArrayList<>();

        for (Parcela parcela : parcelas) {
            resposta.add(new ParcelaResponse(parcela));
        }

        return resposta;
    }
}