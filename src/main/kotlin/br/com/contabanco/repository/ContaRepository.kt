package br.com.contabanco.repository

import br.com.contabanco.model.Conta
import io.micronaut.context.annotation.Parameter
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository


@Repository
interface ContaRepository: JpaRepository<Conta, Int> {

    @Query(value = "SELECT valor_saldo FROM conta WHERE codigo_conta = :codigoConta", nativeQuery = true)
    fun buscaSaldoConta(@Parameter("codigoConta") codigoConta: Int): Float
}