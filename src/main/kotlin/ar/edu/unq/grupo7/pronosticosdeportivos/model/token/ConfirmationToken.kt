package ar.edu.unq.grupo7.pronosticosdeportivos.model.token

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import java.time.LocalDateTime
import javax.persistence.*


/*@Entity
class ConfirmationToken(@Column(nullable = false) var token: String,
                        @Column(nullable = false) var createdAt: LocalDateTime,
                        @Column(nullable = false) var expiresAt: LocalDateTime ) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    var confirmedAt: LocalDateTime? = null
        private set

    @ManyToOne
    @JoinColumn(name = "user_id")
    private var user: User? = null

    constructor(token: String, createdAt: LocalDateTime, expiresAt: LocalDateTime, user: User?) {
        this.token = token
        this.createdAt = createdAt
        this.expiresAt = expiresAt
        this.confirmedAt = confirmedAt
        this.user = user
    }

    fun getUser(): User? {
        return user
    }
}*/

@Entity
class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    @Column(nullable = false)
    var token: String = ""
        private set

    @Column
    private var createdAt: LocalDateTime? = null

    @Column
    private var expiresAt: LocalDateTime? = null

    private var isConfirmed: Boolean = false

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    var user: User? = null
        get() = field
        private set

    constructor() {}
    constructor(token: String, createdAt: LocalDateTime?, expiresAt: LocalDateTime?, user: User?) {
        this.token = token
        this.createdAt = createdAt
        this.expiresAt = expiresAt
        this.user = user
    }

    fun getExpiresAt(): LocalDateTime? {
        return this.expiresAt
    }

    fun getConfirmed(): Boolean{
        return this.isConfirmed
    }
}