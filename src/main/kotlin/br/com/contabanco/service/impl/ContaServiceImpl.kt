package br.com.contabanco.service.impl

import br.com.contabanco.dto.ContaDTO
import br.com.contabanco.model.Conta
import br.com.contabanco.repository.ContaRepository
import br.com.contabanco.service.ContaService
import io.micronaut.context.annotation.Bean
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import org.slf4j.LoggerFactory

@Bean
class ContaServiceImpl(private val repository: ContaRepository) : ContaService {

    private val log = LoggerFactory.getLogger(ContaServiceImpl::class.java)

    override fun buscarTodasContas(): List<Conta> = repository.findAll()

    override fun novaConta(contaDTO: ContaDTO): Conta {
        val conta: Conta = contaDTO.insertConta()
        log.info("Conta criada com sucesso!")
        return repository.save(conta)
    }

    override fun consultarSaldoConta(codigoConta: Int): Float = repository.buscaSaldoConta(codigoConta)

    override fun apagaConta(idConta: Int): HttpResponse<Map<String, String?>> {
        try {
            validaItem(idConta)
            repository.deleteById(idConta)
            log.info("Cliente deletado com sucesso!")
            return HttpResponse.ok(mapOf("resposta" to "Cliente deletado com sucesso!"))
        } catch (e: Exception) {
            log.error("Cliente inexistente")
            return HttpResponse.badRequest(mapOf("resposta" to "Cliente inexistente!"))

        }
    }

    override fun debitarConta(idConta: Int, valor_debito: Float): HttpResponse<Map<String, String?>> {
        val contaDb = repository.findById(idConta)
        if (contaDb.isPresent) {
            if (contaDb.get().valorSaldo >= valor_debito) {
                val new_saldo = contaDb.get().valorSaldo - valor_debito
                val c = Conta(
                    contaDb.get().codigoConta, contaDb.get().nomeTipoConta, contaDb.get().nomeConta,
                    new_saldo, contaDb.get().codigoCliente
                )
                repository.update(c)
                log.info("Valor debitado com sucesso!")
                return HttpResponse.ok(mapOf("resposta" to "Valor debitado com sucesso!"))
            } else {
                log.error("SALDO INSUFICIENTE!")
                return HttpResponse.badRequest(mapOf("resposta" to "SALDO INSUFICIENTE!"))
            }
        }
        return HttpResponse.status(HttpStatus.NOT_FOUND, "CONTA NAO ENCONTRADA")
    }

    override fun creditarConta(idConta: Int, valor_creditar: Float): HttpResponse<Map<String, String?>> {
        val contaCr = repository.findById(idConta)
        if (contaCr.isPresent) {
            val new_saldo = contaCr.get().valorSaldo + valor_creditar
            val c = Conta(contaCr.get().codigoConta, contaCr.get().nomeTipoConta, contaCr.get().nomeConta,
                          new_saldo, contaCr.get().codigoCliente)
            repository.update(c)
            log.info("Valor creditado com sucesso!")
            return HttpResponse.ok(mapOf("resposta" to "Valor creditado com sucesso!"))
        }else {
            log.error("Valor informado inexistente")
            return HttpResponse.notFound(mapOf("resposta" to "Conta informada inexistente!"))
        }
    }

    private fun validaItem(idConta: Int): Conta? {
        return repository.findById(idConta).orElseThrow()
    }
}