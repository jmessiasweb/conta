package br.com.contabanco.service.impl

import br.com.contabanco.model.Cliente
import br.com.contabanco.repository.ClienteRepository
import br.com.contabanco.service.ClienteService
import io.micronaut.context.annotation.Bean
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import org.slf4j.LoggerFactory


@Bean
class ClienteServiceImpl(private val repository: ClienteRepository) : ClienteService {

    private val log = LoggerFactory.getLogger(ClienteServiceImpl::class.java)

    override fun buscarTodosClientes(): HttpResponse<List<Cliente>> {
        try {
            log.error("Consulta de Cliente realizada com sucesso")
            return HttpResponse.ok(repository.findAll())
        } catch (e: Exception) {
            log.error("Erro ao Consultar Cliente")
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, "Error ao consultar cliente")
        }
    }

    override fun buscarClientePorNome(nome: String): HttpResponse<Cliente> {
        val cliente = repository.buscarClientePorNome(nome)
        if (cliente.isPresent) {
            log.error("Cliente nao encontrado")
            return HttpResponse.status(HttpStatus.NOT_FOUND, "Cliente nao encontrado")
        }
        return HttpResponse.ok(cliente.get())
    }

    override fun novoCliente(cliente: Cliente): HttpResponse<Map<String, String?>> {
        TODO("Not yet implemented")
    }

    override fun atualizaCliente(
        idCliente: Int,
        novoNome: String?,
        novoCpf: String?
    ): HttpResponse<Map<String, String?>> {
        TODO("Not yet implemented")
    }

    override fun apagaCliente(idCliente: Int): HttpResponse<Map<String, String?>> {
        TODO("Not yet implemented")
    }

    private fun validaItem(idCliente: Int): Cliente? {
        return repository.findById(idCliente).orElseThrow()
    }
}