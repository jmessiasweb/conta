package br.com.contabanco.controller

import br.com.contabanco.model.Cliente
import br.com.contabanco.service.ClienteService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*


@Controller
class ClienteController(val service: ClienteService) {

    @Get("/cliente")
    fun buscarCliente(@QueryValue("nome") nome: String): HttpResponse<Cliente> {
        return service.buscarClientePorNome(nome)
    }

    @Get("/clientes")
    fun buscarClientes(): HttpResponse<List<Cliente>> {
        return service.buscarTodosClientes()

    }

    @Post("/cliente")
    fun novoCliente(@Body cliente: Cliente): HttpResponse<Map<String, String?>> {
        return  service.novoCliente(cliente)
    }

    @Patch(uri = "/cliente", produces = [MediaType.APPLICATION_JSON])
    fun novoCliente(
        @QueryValue("codigo_cliente") codigoCliente: Int,
        @QueryValue("novo_nome") novo_nome: String?,
        @QueryValue("novo_cpf") novo_cpf: String?
    ): HttpResponse<Map<String, String?>> {
        return service.atualizaCliente(codigoCliente, novo_nome, novo_cpf)
    }

    @Delete("/cliente")
    fun apagaCliente(@QueryValue("codigo_cliente") idCliente: Int): HttpResponse<Map<String, String?>> {
        return service.apagaCliente(idCliente)
    }
}