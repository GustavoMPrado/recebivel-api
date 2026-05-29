package br.com.gustavo.recebivel.cliente;

import br.com.gustavo.recebivel.erro.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveBuscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente clienteEncontrado = clienteService.buscarPorId(1L);

        assertEquals(1L, clienteEncontrado.getId());
        assertEquals("Cliente Teste", clienteEncontrado.getNome());
    }

    @Test
    void deveLancarErroQuandoClienteNaoExistir() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> clienteService.buscarPorId(99L));
    }

    @Test
    void deveDesativarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setAtivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.desativar(1L);

        assertFalse(cliente.getAtivo());
        verify(clienteRepository).save(cliente);
    }
}