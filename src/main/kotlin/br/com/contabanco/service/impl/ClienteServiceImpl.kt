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
        try {
            val novoCliente = repository.save(cliente)
            return HttpResponse.created(
                mapOf(
                    "resposta" to "Cliente criado com sucesso!", "codigo_Cliente" to
                            novoCliente.codigoCliente.toString(), "nome_Cliente" to novoCliente.nomeCliente.toString(),
                    "cpf_Cliente" to novoCliente.cpfCliente.toString()
                )
            )
        } catch (e: Exception) {
            log.error("Cliente ja existe")
            return HttpResponse.badRequest(
                mapOf(
                    "resposta" to "Cliente ja cadastrado na base!",
                    "cpf" to cliente.cpfCliente
                )
            )
        }
    }

    override fun atualizaCliente(
        idCliente: Int,
        novoNome: String?,
        novoCpf: String?
    ): HttpResponse<Map<String, String?>> {
        val cliente = repository.findById(idCliente)
        if (cliente.isPresent) {
            log.info("Pesquisa realizada com sucesso. {}", cliente)
            when {
                novoNome != null && novoCpf == null -> {
                    val novoCliente = Cliente(cliente.get().codigoCliente, novoNome, cliente.get().cpfCliente)
                    val clienteAtualizado = repository.update(novoCliente)
                    log.info("Nome alterado com sucesso")
                    return HttpResponse.ok(
                        mapOf(
                            "resposta" to "Cliente atualizado com sucesso",
                            "nome_cliente_atualizado" to clienteAtualizado.nomeCliente
                        )
                    )
                }
                novoNome != null && novoCpf != null -> {
                    val novoCliente = Cliente(idCliente, novoNome, novoCpf)
                    val clienteAtualizado = repository.update(novoCliente)
                    log.info("Nome e CPF alterados com sucesso")
                    return HttpResponse.ok(
                        mapOf(
                            "resposta" to "Cliente atualizado com sucesso!",
                            "cpf_atualizado" to clienteAtualizado.cpfCliente, "nome_cliente_atualizado" to
                                    clienteAtualizado.nomeCliente
                        )
                    )
                } else -> {
                    log.error("Valores de Atualização não informado")
                return HttpResponse.status(HttpStatus.NOT_FOUND, "VALORES de ATUALIZAÇÃO não INFORMADO")
                }
            }
        }else {
            log.error("Cliente não encontrado na base: {}", idCliente)
            return HttpResponse.status(HttpStatus.NOT_FOUND, "CLIENTE NÃO ENCONTRADO")
        }
    }

    override fun apagaCliente(idCliente: Int): HttpResponse<Map<String, String?>> {
        try {
            validaItem(idCliente)
            repository.deleteById(idCliente)
            return HttpResponse.ok(mapOf("resposta" to "Cliente deletado com sucesso"))
        } catch (e: Exception) {
            log.error("Cliente inexistente")
            return HttpResponse.badRequest(mapOf("resposta" to "Cliente deletado com sucesso!"))
        }
    }

    private fun validaItem(idCliente: Int): Cliente? {
        return repository.findById(idCliente).orElseThrow()
    }
}