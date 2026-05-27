package br.com.gustavo.recebivel.cliente;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        List<Cliente> clientes = clienteService.listar();
        List<ClienteResponse> resposta = new ArrayList<>();

        for (Cliente cliente : clientes) {
            resposta.add(new ClienteResponse(cliente));
        }

        return resposta;
    }

    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return new ClienteResponse(cliente);
    }

    @PostMapping
    public ClienteResponse salvar(@RequestBody @Valid Cliente cliente) {
        Cliente clienteSalvo = clienteService.salvar(cliente);
        return new ClienteResponse(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
        Cliente clienteAtualizado = clienteService.atualizar(id, cliente);
        return new ClienteResponse(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ClienteResponse desativar(@PathVariable Long id) {
        Cliente clienteDesativado = clienteService.desativar(id);
        return new ClienteResponse(clienteDesativado);
    }
}
