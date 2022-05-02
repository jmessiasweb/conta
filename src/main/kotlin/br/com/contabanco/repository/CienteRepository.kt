package br.com.contabanco.repository

import br.com.contabanco.model.Cliente
import io.micronaut.context.annotation.Parameter
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface CienteRepository: JpaRepository<Cliente, Int> {

    @Query(value = "SELECT * FROM cliente WHERE nome_cliente = :nome", nativeQuery = true)
    fun buscaClientePorNome(@Parameter("nome") nome: String): Optional<Cliente>
}