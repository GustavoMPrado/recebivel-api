package br.com.gustavo.recebivel.cliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Clientes", description = "Cadastro, consulta, atualização e desativação de clientes.")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Lista todos os clientes")
    @GetMapping
    public List<ClienteResponse> listar() {
        List<Cliente> clientes = clienteService.listar();
        List<ClienteResponse> resposta = new ArrayList<>();

        for (Cliente cliente : clientes) {
            resposta.add(new ClienteResponse(cliente));
        }

        return resposta;
    }

    @Operation(summary = "Busca um cliente por id")
    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return new ClienteResponse(cliente);
    }

    @Operation(summary = "Cadastra um novo cliente")
    @PostMapping
    public ClienteResponse salvar(@RequestBody @Valid Cliente cliente) {
        Cliente clienteSalvo = clienteService.salvar(cliente);
        return new ClienteResponse(clienteSalvo);
    }

    @Operation(summary = "Atualiza um cliente existente")
    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
        Cliente clienteAtualizado = clienteService.atualizar(id, cliente);
        return new ClienteResponse(clienteAtualizado);
    }

    @Operation(summary = "Desativa um cliente")
    @DeleteMapping("/{id}")
    public ClienteResponse desativar(@PathVariable Long id) {
        Cliente clienteDesativado = clienteService.desativar(id);
        return new ClienteResponse(clienteDesativado);
    }
}
