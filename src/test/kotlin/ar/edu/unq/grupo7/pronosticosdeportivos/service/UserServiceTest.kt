package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.UserBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.InvalidNameException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UsedEmailException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.ConfirmationTokenRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var tokenRepository : ConfirmationTokenRepository

    lateinit var user : User

    val userBuilder = UserBuilder()

    @BeforeEach
    fun setUp(){
        tokenRepository.deleteAll()
        userRepository.deleteAll()
        user = userRepository.save(userBuilder.withEmail("enzogalarza2@gmail.com").build())
    }

    @Test
    fun loadByUsername(){

        val loadedUser = userService.loadUserByUsername(user.username)

        assertEquals(loadedUser.username,user.username)
        assertEquals(loadedUser.getName(),user.getName())
    }

    @Test
    fun loadByUsernameNonExistentUser(){
        val newUser = userBuilder.withEmail("EnzoGl33@hotmail.com").build()

        assertThrows<UsernameNotFoundException> {
            userService.loadUserByUsername(newUser.username)
        }
    }

    @Test
    fun signUpUser(){
        val newUser = userBuilder.withEmail("EnzoGl33@outlook.com").build()

        userService.signUpUser(newUser)

        val getUser = userRepository.findByEmail(newUser.username).get()

        assertEquals(newUser.username,getUser.username)
        assertEquals(newUser.getName(),getUser.getName())
    }

    @Test
    fun signUpAlreadyExistentUser(){
        val newUser = userBuilder.withEmail("enzogalarza2@gmail.com").build()

        assertThrows<UsedEmailException> {
            userService.signUpUser(newUser)
        }
    }

    @Test
    fun signUpInvalidUser(){
        val newUser = userBuilder.withEmail("EnzoGl33@outlook.com").withName("p").build()

        assertThrows<InvalidNameException> {
            userService.signUpUser(newUser)
        }
    }

    @Test
    fun getUsersEmails(){
        val user1 = userBuilder.withEmail("EnzoGl33@outlook.com").build()
        val user2 = userBuilder.withEmail("EnzoGl33@hotmail.com").build()

        userRepository.save(user1)
        userRepository.save(user2)

        val otherUsersEmails = userService.getUsersEmails(user.username)

        assertEquals(otherUsersEmails.size,2)
        Assertions.assertTrue(otherUsersEmails.contains(user1.username))
        Assertions.assertTrue(otherUsersEmails.contains(user2.username))
    }
}