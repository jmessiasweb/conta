package br.com.contabanco.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith
internal class ContaDTOTest {

    @Test
    fun testaInsertConta() {
        val contaDTO = ContaDTO("1", "teste", 12.8F, 1);
        val conta = contaDTO.insertConta()
        assertNull(conta.codigoConta)
        assertEquals(12.8F, conta.valorSaldo)

    }
}