package br.com.gustavo.recebivel.cobranca;

import br.com.gustavo.recebivel.cliente.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CobrancaServiceTest {

    @Mock
    private CobrancaRepository cobrancaRepository;

    @Mock
    private ParcelaRepository parcelaRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CobrancaService cobrancaService;

    @Test
    void deveRegistrarPagamentoDeParcelaPendente() {
        Cobranca cobranca = new Cobranca();
        cobranca.setId(1L);
        cobranca.setStatus(StatusCobranca.ABERTA);

        Parcela parcela = new Parcela();
        parcela.setId(1L);
        parcela.setNumero(1);
        parcela.setValor(new BigDecimal("100.00"));
        parcela.setDataVencimento(LocalDate.now());
        parcela.setStatus(StatusParcela.PENDENTE);
        parcela.setCobranca(cobranca);

        when(parcelaRepository.findById(1L)).thenReturn(Optional.of(parcela));
        when(parcelaRepository.save(parcela)).thenReturn(parcela);
        when(parcelaRepository.findByCobrancaId(1L)).thenReturn(List.of(parcela));

        Parcela parcelaPaga = cobrancaService.registrarPagamento(1L);

        assertEquals(StatusParcela.PAGA, parcelaPaga.getStatus());
        assertNotNull(parcelaPaga.getDataPagamento());
        assertEquals(StatusCobranca.PAGA, cobranca.getStatus());

        verify(parcelaRepository).save(parcela);
        verify(cobrancaRepository).save(cobranca);
    }

    @Test
    void deveBloquearPagamentoDuplicadoDeParcela() {
        Parcela parcela = new Parcela();
        parcela.setId(1L);
        parcela.setStatus(StatusParcela.PAGA);

        when(parcelaRepository.findById(1L)).thenReturn(Optional.of(parcela));

        IllegalStateException erro = assertThrows(
                IllegalStateException.class,
                () -> cobrancaService.registrarPagamento(1L)
        );

        assertEquals("Parcela já está paga.", erro.getMessage());
    }

    @Test
    void deveBloquearPagamentoDeParcelaCancelada() {
        Parcela parcela = new Parcela();
        parcela.setId(1L);
        parcela.setStatus(StatusParcela.CANCELADA);

        when(parcelaRepository.findById(1L)).thenReturn(Optional.of(parcela));

        IllegalStateException erro = assertThrows(
                IllegalStateException.class,
                () -> cobrancaService.registrarPagamento(1L)
        );

        assertEquals("Parcela cancelada não pode ser paga.", erro.getMessage());
    }

    @Test
    void devePermitirPagamentoDeParcelaVencida() {
        Cobranca cobranca = new Cobranca();
        cobranca.setId(1L);
        cobranca.setStatus(StatusCobranca.ABERTA);

        Parcela parcela = new Parcela();
        parcela.setId(1L);
        parcela.setStatus(StatusParcela.VENCIDA);
        parcela.setCobranca(cobranca);

        when(parcelaRepository.findById(1L)).thenReturn(Optional.of(parcela));
        when(parcelaRepository.save(parcela)).thenReturn(parcela);
        when(parcelaRepository.findByCobrancaId(1L)).thenReturn(List.of(parcela));

        Parcela parcelaPaga = cobrancaService.registrarPagamento(1L);

        assertEquals(StatusParcela.PAGA, parcelaPaga.getStatus());
        assertNotNull(parcelaPaga.getDataPagamento());
        assertEquals(StatusCobranca.PAGA, cobranca.getStatus());

        verify(parcelaRepository).save(parcela);
        verify(cobrancaRepository).save(cobranca);
    }

    @Test
    void deveCancelarCobrancaEParcelasPendentes() {
        Cobranca cobranca = new Cobranca();
        cobranca.setId(1L);
        cobranca.setStatus(StatusCobranca.ABERTA);

        Parcela parcelaPendente = new Parcela();
        parcelaPendente.setId(1L);
        parcelaPendente.setStatus(StatusParcela.PENDENTE);

        Parcela parcelaPaga = new Parcela();
        parcelaPaga.setId(2L);
        parcelaPaga.setStatus(StatusParcela.PAGA);

        when(cobrancaRepository.findById(1L)).thenReturn(Optional.of(cobranca));
        when(parcelaRepository.findByCobrancaId(1L)).thenReturn(List.of(parcelaPendente, parcelaPaga));
        when(cobrancaRepository.save(cobranca)).thenReturn(cobranca);

        Cobranca cobrancaCancelada = cobrancaService.cancelar(1L);

        assertEquals(StatusCobranca.CANCELADA, cobrancaCancelada.getStatus());
        assertEquals(StatusParcela.CANCELADA, parcelaPendente.getStatus());
        assertEquals(StatusParcela.PAGA, parcelaPaga.getStatus());

        verify(parcelaRepository).save(parcelaPendente);
        verify(cobrancaRepository).save(cobranca);
    }

    @Test
    void deveMarcarParcelasPendentesVencidas() {
        Parcela parcela = new Parcela();
        parcela.setId(1L);
        parcela.setStatus(StatusParcela.PENDENTE);
        parcela.setDataVencimento(LocalDate.now().minusDays(1));

        List<Parcela> parcelas = List.of(parcela);

        when(parcelaRepository.findByStatusAndDataVencimentoBefore(
                StatusParcela.PENDENTE,
                LocalDate.now()
        )).thenReturn(parcelas);

        when(parcelaRepository.saveAll(parcelas)).thenReturn(parcelas);

        List<Parcela> parcelasVencidas = cobrancaService.marcarParcelasVencidas();

        assertEquals(1, parcelasVencidas.size());
        assertEquals(StatusParcela.VENCIDA, parcela.getStatus());

        verify(parcelaRepository).saveAll(parcelas);
    }

    @Test
    void deveCriarCobrancaComParcelas() {
        br.com.gustavo.recebivel.cliente.Cliente cliente = new br.com.gustavo.recebivel.cliente.Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        CriarCobrancaRequest request = new CriarCobrancaRequest();
        request.setDescricao("Cobrança Teste");
        request.setValorTotal(new BigDecimal("300.00"));
        request.setDataEmissao(LocalDate.now());
        request.setDataVencimento(LocalDate.now().plusDays(10));
        request.setQuantidadeParcelas(3);
        request.setClienteId(1L);

        Cobranca cobrancaSalva = new Cobranca();
        cobrancaSalva.setId(1L);
        cobrancaSalva.setDescricao(request.getDescricao());
        cobrancaSalva.setValorTotal(request.getValorTotal());
        cobrancaSalva.setDataEmissao(request.getDataEmissao());
        cobrancaSalva.setDataVencimento(request.getDataVencimento());
        cobrancaSalva.setStatus(StatusCobranca.ABERTA);
        cobrancaSalva.setCliente(cliente);

        when(clienteService.buscarPorId(1L)).thenReturn(cliente);
        when(cobrancaRepository.save(org.mockito.ArgumentMatchers.any(Cobranca.class))).thenReturn(cobrancaSalva);

        Cobranca cobrancaCriada = cobrancaService.salvar(request);

        assertEquals("Cobrança Teste", cobrancaCriada.getDescricao());
        assertEquals(new BigDecimal("300.00"), cobrancaCriada.getValorTotal());
        assertEquals(StatusCobranca.ABERTA, cobrancaCriada.getStatus());
        assertEquals(cliente, cobrancaCriada.getCliente());

        verify(cobrancaRepository).save(org.mockito.ArgumentMatchers.any(Cobranca.class));
        verify(parcelaRepository, org.mockito.Mockito.times(3)).save(org.mockito.ArgumentMatchers.any(Parcela.class));
    }
}