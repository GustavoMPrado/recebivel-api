package br.com.gustavo.recebivel.cliente;

import br.com.gustavo.recebivel.erro.RecursoNaoEncontradoException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarPorId(id);

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setDocumento(clienteAtualizado.getDocumento());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());

        return clienteRepository.save(cliente);
    }

    public Cliente desativar(Long id) {
        Cliente cliente = buscarPorId(id);

        cliente.setAtivo(false);

        return clienteRepository.save(cliente);
    }
}
