package br.com.contabanco.service

import br.com.contabanco.model.Cliente
import io.micronaut.context.annotation.Bean
import io.micronaut.http.HttpResponse


@Bean
interface ClienteService {

    fun buscarTodosClientes(): HttpResponse<List<Cliente>>

    fun buscarClientePorNome(nome: String): HttpResponse<Cliente>

    fun novoCliente(cliente: Cliente): HttpResponse<Map<String, String?>>

    fun atualizaCliente(idCliente: Int, novoNome: String?, novoCpf: String?): HttpResponse<Map<String, String?>>

    fun apagaCliente(idCliente: Int): HttpResponse<Map<String, String?>>
}