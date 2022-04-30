package br.com.contabanco.dto

import br.com.contabanco.model.Cliente
import br.com.contabanco.model.Conta
import com.fasterxml.jackson.annotation.JsonProperty


data class ContaDTO(
    @JsonProperty("nomeTipoConta")
    val nomeTipoConta: String,

    @JsonProperty("nomeConta")
    val nomeConta: String,

    @JsonProperty("valorSaldo")
    val valorSaldo: Float,

    @JsonProperty("codigoCliente")
    var codigoCliente: Int
) {
    fun insertConta(): Conta {
        val cliente = Cliente(codigoCliente, null, null)
        return Conta(null, nomeTipoConta, nomeConta, valorSaldo, cliente)
    }
}
