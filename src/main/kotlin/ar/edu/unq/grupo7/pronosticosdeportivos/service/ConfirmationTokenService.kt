package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.EmailAlreadyConfirmedException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.TokenExpiredException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.TokenNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.token.ConfirmationToken
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.ConfirmationTokenRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import java.util.*
import java.util.function.Supplier

@Service
class ConfirmationTokenService(
    confirmationTokenRepository: ConfirmationTokenRepository,
    userRepository: UserRepository
) {
    private val confirmationTokenRepository: ConfirmationTokenRepository
    private val userRepository: UserRepository

    //Constructor
    init {
        this.confirmationTokenRepository = confirmationTokenRepository
        this.userRepository = userRepository
    }

    fun saveConfirmationToken(token: ConfirmationToken) {
        confirmationTokenRepository.save(token)
    }

    fun getToken(token: String): Optional<ConfirmationToken> {
        return confirmationTokenRepository.findByToken(token)
    }

    fun getTokenByUser(user: User): String {
        return confirmationTokenRepository.findByUser(user).token
    }

    fun setConfirmedAt(token: String?): Int {
        return confirmationTokenRepository.updateConfirmed(
            token, true
        )
    }

    @Transactional
    fun confirmToken(token: String): String {
        val confirmationToken: ConfirmationToken = getToken(token)
            .orElseThrow(Supplier<RuntimeException> { TokenNotFoundException() })
        if (confirmationToken.getConfirmed()) {
            throw EmailAlreadyConfirmedException()
        }
        val expiredAt: LocalDateTime? = confirmationToken.getExpiresAt()
        if (expiredAt!!.isBefore(LocalDateTime.now())) {
            throw TokenExpiredException()
        }
        setConfirmedAt(token)
        enableAppUser(confirmationToken.user!!.username)
        return "¡Token confirmado! Ya podés loguearte en la app"
    }

    fun enableAppUser(email: String?): Int {
        return userRepository.enableAppUser(email)
    }
}