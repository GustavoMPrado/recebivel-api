package br.com.gustavo.recebivel.cobranca;

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

    @PostMapping
    public Cobranca salvar(@RequestBody CriarCobrancaRequest request) {
        return cobrancaService.salvar(request);
    }
}