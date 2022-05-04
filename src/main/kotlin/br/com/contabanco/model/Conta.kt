package br.com.contabanco.model

import io.micronaut.data.annotation.Id
import javax.persistence.*

@Entity
@Table(name = "cliente")
data class Conta(

    @Id
    @Column(name = "codigo_conta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val codigoConta: Int?,

    @Column(name = "nome_tipo_conta", nullable = false)
    val nomeTipoConta: String,

    @Column(name = "nome_conta", nullable = false)
    val nomeConta: String?,

    @Column(name = "valor_saldo", nullable = false)
    val valorSaldo: Float,

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Cliente::class)
    @JoinColumn(name = "codigo_cliente")
    val codigoCliente: Cliente?
) {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
}
