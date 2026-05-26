package br.com.gustavo.recebivel.cobranca;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cobrancas")
public class CobrancaController {

    private final CobrancaService cobrancaService;

    public CobrancaController(CobrancaService cobrancaService) {
        this.cobrancaService = cobrancaService;
    }

    @GetMapping
    public List<Cobranca> listar() {
        return cobrancaService.listar();
    }

    @GetMapping("/{id}")
    public Cobranca buscarPorId(@PathVariable Long id) {
        return cobrancaService.buscarPorId(id);
    }

    @GetMapping("/{id}/parcelas")
    public List<Parcela> listarParcelas(@PathVariable Long id) {
        return cobrancaService.listarParcelas(id);
    }

    @PostMapping
    public Cobranca salvar(@RequestBody @Valid CriarCobrancaRequest request) {
        return cobrancaService.salvar(request);
    }

    @PutMapping("/parcelas/{id}/pagamento")
    public Parcela registrarPagamento(@PathVariable Long id) {
        return cobrancaService.registrarPagamento(id);
    }

    @PutMapping("/{id}/cancelamento")
    public Cobranca cancelar(@PathVariable Long id) {
        return cobrancaService.cancelar(id);
    }

    @PutMapping("/parcelas/vencidas")
    public List<Parcela> marcarParcelasVencidas() {
        return cobrancaService.marcarParcelasVencidas();
    }
}