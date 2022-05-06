package br.com.contabanco.controller

import br.com.contabanco.model.Cliente
import br.com.contabanco.service.ClienteService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito

@ExtendWith
internal class ClienteControllerTest {

    private lateinit var clienteService: ClienteService
    private lateinit var clienteController: ClienteController

    @BeforeAll
    fun setUp() {
        clienteService = Mockito.mock(ClienteService::class.java)
        clienteController = ClienteController(clienteService)
    }

    @Test
    fun testaBuscarClientePorNomeSeEstaoTodosOsParametrosPreenchidos() {
        Mockito.`when`(clienteService.buscarClientePorNome(nome = Mockito.anyString()))
            .thenReturn(HttpResponse.ok(Cliente(1, "testando", "123456789096")))
        assertNull(clienteController.buscarCliente("teste").body().nomeCliente)
        assertNull(clienteController.buscarCliente("dedede").body().codigoCliente)
        assertNull(clienteController.buscarCliente("123435331").body().cpfCliente)
    }

    @Test
    fun testaBuscarTodosClientesSeEstaoTodosCamposPreenchidos() {
        Mockito.`when`(clienteService.buscarTodosClientes())
            .thenReturn(
                HttpResponse.ok(
                    listOf(
                        Cliente(
                            codigoCliente = 1,
                            nomeCliente = "teste",
                            cpfCliente = "21123454367"
                        )
                    )
                )
            )
        assertNotNull(clienteController.buscarClientes())
    }

    @Test
    fun testaIncluirNovoCliente() {
        val novoCliente = Cliente(1, "vc", "23458906541")
        Mockito.`when`(clienteService.novoCliente(novoCliente)).thenReturn(
            HttpResponse.created(
                mapOf(
                    "resposta" to "Clientecriado com sucesso!",
                    "codigo_Cliente" to novoCliente.codigoCliente.toString(),
                    "nome_Cliente" to novoCliente.nomeCliente.toString(),
                    "cpf_Cliente" to novoCliente.cpfCliente.toString()
                )
            )
        )
    }

    @Test
    fun testaAtualizacaoCpfNomeCliente() {
        Mockito.`when`(clienteService.atualizaCliente(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(
                HttpResponse.ok(
                    mapOf(
                        "resposta" to "Cliente atualizado com sucesso!",
                        "cpf_atualizado" to "Nome Alterado",
                        "nome_cliente_atualizado" to "012345678903"
                    )
                )
            )
        assertEquals(HttpStatus.OK, clienteController.novoCliente(1,"Nome Alterado",
                                                                  "1234").status)
    }


}