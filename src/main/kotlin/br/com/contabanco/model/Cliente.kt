package br.com.contabanco.model

import javax.persistence.*

@Entity
@Table(name = "cliente")
data class Cliente(

    @Id
    @Column(name = "codigo_cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val codigoCliente: Int?,

    @Column(name = "nome_cliente", nullable = false)
    val nomeCliente: String?,

    @Column(name = "numero_cpf", nullable = false, unique = true)
    val cpfCliente: String?
)
