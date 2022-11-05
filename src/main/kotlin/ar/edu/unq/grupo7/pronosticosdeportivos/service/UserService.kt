package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.Email
import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.MessageBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.Sender
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UsedEmailException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UserNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.token.ConfirmationToken
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.Notification
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
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

    private val messageBuilder: MessageBuilder = MessageBuilder()

    @Autowired
    private lateinit var userRepository : UserRepository

    @Autowired
    private lateinit var confirmationTokenService : ConfirmationTokenService

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): User {
        return userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException(String.format(UserService.USER_NOT_FOUND, email)) }
    }

    fun signUpUser(user: User) {
        val userExists: Boolean = userRepository.findByEmail(user.username).isPresent
        if(userExists) {
            throw UsedEmailException("El email ${user.username} ya fué utilizado")
        }
        user.validate()
        userRepository.save(user)
        val token = UUID.randomUUID().toString()
        val confirmationToken = ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        )
        confirmationTokenService.saveConfirmationToken(confirmationToken)
        val sender = Sender("Client send", this.buildConfirmEmail(user.username, user.getName(), token))
        sender.start()
    }

    private fun buildConfirmEmail(receiver: String, name: String, token: String): Email {
        val email = Email()
        val link = "http://localhost:8080/confirmToken?token=$token"
        email.composeEmailWith("Confirmá tu email", mutableListOf(receiver), messageBuilder.confirmEmailMessage(name, link))
        return email
    }

    fun getUsersEmails(user: String): List<String> {
        return userRepository.findUsersEmail(user)
    }

    @Transactional
    fun deleteNotification(userId: Long, notification: Notification) {
        var user = userRepository.findById(userId).orElseThrow { UserNotFoundException("No se encontró al usuario") }
        user.deleteNotification(notification)
        userRepository.save(user)
    }

}