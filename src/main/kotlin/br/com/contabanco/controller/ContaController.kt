package br.com.contabanco.controller

import br.com.contabanco.dto.ContaDTO
import br.com.contabanco.model.Conta
import br.com.contabanco.service.ContaService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Controller
class ContaController(val service: ContaService) {

    @Get("/contas")
    fun buscarTodasContas(): List<Conta> {
        return buscarTodasContas()
    }

    @Get("/conta/saldo")
    fun buscarSaldoConta(@QueryValue("codigo_conta") codigoConta: Int): Float {
        return service.consultarSaldoConta(codigoConta)
    }

    @Post("/conta")
    @Status(HttpStatus.CREATED)
    fun criarConta(@Body contaDTO: ContaDTO): HttpResponse<Conta> {
        return HttpResponse.created(service.novaConta(contaDTO))
    }
    @Patch("/conta/debito")
    fun debitarConta(@QueryValue("codigo_conta") codigoConta: Int, @QueryValue("valor_debito")
                     valor_debito: Float): HttpResponse<Map<String, String?>> {
        return service.debitarConta(codigoConta, valor_debito)
    }

    @Patch("/conta/credito")
    fun creditarConta(@QueryValue("codigo_conta") codigoConta: Int, @QueryValue("valor_credito")
                      valor_credito: Float):HttpResponse<Map<String, String?>> {
        return service.creditarConta(codigoConta, valor_credito)
    }

    @Delete("/conta")
    fun apagarConta(@QueryValue(value = "codigo_conta") idConta: Int): HttpResponse<Map<String, String?>> {
        return service.apagaConta(idConta)
    }
}