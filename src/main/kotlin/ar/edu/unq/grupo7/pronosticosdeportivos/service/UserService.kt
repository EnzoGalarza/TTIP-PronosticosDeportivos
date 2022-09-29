package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.Email
import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.MessageBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.Sender
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.InvalidEmailException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UsedEmailException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.token.ConfirmationToken
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.model.validations.EmailValidator
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserService: UserDetailsService {

    companion object {
        private const val USER_NOT_FOUND = "Usuario %s no encontrado"
    }

    private val emailValidator: EmailValidator = EmailValidator()

    private val messageBuilder: MessageBuilder = MessageBuilder()

    @Autowired
    private lateinit var userRepository : UserRepository

    @Autowired
    private lateinit var confirmationTokenService : ConfirmationTokenService

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException(String.format(UserService.USER_NOT_FOUND, email)) }
    }

    fun registerUser(user: User) {
        val isValidEmail: Boolean = this.emailValidator.test(user.username)
        if (!isValidEmail) {
            throw InvalidEmailException(user.username)
        }
        this.signUpUser(user)
    }

    fun signUpUser(user: User) {
        val userExists: Boolean = userRepository.findByEmail(user.username).isPresent
        if (userExists) {
            throw UsedEmailException(user.username)
        }
        userRepository.save(user)
        val token = UUID.randomUUID().toString()
        val confirmationToken = ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        )
        confirmationTokenService.saveConfirmationToken(confirmationToken)
        val sender = Sender(this.buildConfirmEmail(user.username, user.getName(), token))
        sender.send()
    }

    private fun buildConfirmEmail(reciver: String, name: String, token: String): Email {
        val email = Email()
        val link = "http://localhost:8080/confirmToken?token=$token"
        email.composeEmailWith("Confirmá tu email", reciver, messageBuilder.confirmEmailMessage(name, link))
        return email
    }

}