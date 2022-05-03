package br.com.contabanco.service

import br.com.contabanco.dto.ContaDTO
import br.com.contabanco.model.Conta
import io.micronaut.context.annotation.Bean
import io.micronaut.http.HttpResponse


@Bean
interface ContaService {

    fun buscarTodasContas(): List<Conta>

    fun novaConta(contaDTO: ContaDTO): Conta

    fun consultarSaldoConta(codigoConta: Int): Float

    fun apagaConta(idConta: Int): HttpResponse<Map<String, String?>>

    fun debitarConta(idConta: Int, debitar: Float): HttpResponse<Map<String, String?>>

    fun creditarConta(idConta: Int, creditar: Float): HttpResponse<Map<String, String?>>
}