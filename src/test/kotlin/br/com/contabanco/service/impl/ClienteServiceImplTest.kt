package br.com.contabanco.service.impl

import br.com.contabanco.model.Cliente
import br.com.contabanco.repository.ClienteRepository
import br.com.contabanco.service.ClienteService
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

@ExtendWith
internal class ClienteServiceImplTest {

    private lateinit var clienteRepository: ClienteRepository
    private lateinit var clienteService: ClienteService

    @BeforeEach
    fun setUp() {
        clienteRepository = mock(ClienteRepository::class.java)
        clienteService = ClienteServiceImpl(clienteRepository)
    }
    @Test
    fun testaAtualizaCpfNomeClientes() {
        `when`(clienteRepository.findById(Mockito.anyInt())).thenReturn(getCliente())
        `when`(clienteRepository.update(Mockito.any())).thenReturn(getClienteSave())

        assertEquals(HttpStatus.OK, clienteService.atualizaCliente(1, "nome", "12345").status)
    }

    private fun getClienteSave(): Cliente? {
        return (Cliente(1, "nome", "12345"))

    }

    private fun getCliente(): Optional<Cliente>? {
        return Optional.of(Cliente(1, "nome", "12345"))

    }

    private fun getClienteVazio(): Optional<Cliente>? {
        return Optional.empty()
    }

    @Test
    fun testaAtualizacaoNomeCliente() {
        `when`(clienteRepository.findById(Mockito.anyInt())).thenReturn(getCliente())
        `when`(clienteRepository.update(Mockito.any())).thenReturn(getClienteSave())

        assertEquals(HttpStatus.OK, clienteService.atualizaCliente(1, "nome", null).status)
    }

    @Test
    fun testaAtualizaCpfCliente() {
        `when`(clienteRepository.findById(Mockito.anyInt())).thenReturn(getCliente())
        `when`(clienteRepository.update(Mockito.any())).thenReturn(getClienteSave())

        assertEquals(HttpStatus.OK, clienteService.atualizaCliente(1, null, "12345").status)
    }

    @Test
    fun testaClienteNaoEncontrado() {
        `when`(clienteRepository.findById(Mockito.anyInt())).thenReturn(getClienteVazio())

        assertEquals(HttpStatus.NOT_FOUND, clienteService.atualizaCliente(1, null, null).status)
    }



    @Test
    fun testaBuscaSeClientesSeEstaOsCamposPreenchidos() {
        `when`(clienteRepository.findAll())
            .thenReturn(listOf(Cliente(codigoCliente = null, nomeCliente = "nome", cpfCliente = "12345")))
        assertNotNull(clienteService.buscarTodosClientes().body().first().nomeCliente)
        assertNotNull(clienteService.buscarTodosClientes().body().first().cpfCliente)
        assertNotNull(clienteService.buscarTodosClientes().body().first().codigoCliente)
    }

    @Test
    fun testaBuscarClientePorNomeSeEstaTodosOsParametrosPreenchidos() {
        `when`(clienteRepository.buscarClientePorNome(nome = Mockito.anyString()))
            .thenReturn(Optional.of(Cliente(1, "nome", "12345")) )
        assertNotNull(clienteService.buscarClientePorNome("nome").body().nomeCliente)
        assertNotNull(clienteService.buscarClientePorNome("teste").body().codigoCliente)
        assertNotNull(clienteService.buscarClientePorNome("saudade").body().cpfCliente)
    }

    @Test
    fun testaIncluirNovoCliente() {
        val cliente = Cliente(1, "nome", "12345")
        `when`(clienteRepository.save(Mockito.any())).thenReturn(getClienteSave())
        assertEquals(HttpStatus.CREATED, clienteService.novoCliente(cliente).status)
    }

    @Test
    fun testaSeOClienteFoiApagado() {
        Mockito.doNothing().`when`(clienteRepository).deleteById(Mockito.anyInt())
        `when`(clienteRepository.findById(Mockito.anyInt())).thenReturn(getCliente())
        assertEquals(HttpStatus.OK, clienteService.apagaCliente(1).status)
    }
}