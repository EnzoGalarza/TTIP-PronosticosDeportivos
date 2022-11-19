package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.UserBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.RegisterDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.Notification
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var userRepository : UserRepository

    val userBuilder = UserBuilder()

    lateinit var userToRegister : RegisterDTO

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        mapper = ObjectMapper()
        userToRegister = RegisterDTO("nombre","enzogalarza2@gmail.com","123","imagen.png")
    }

    @Test
    @WithMockUser("email")
    fun registerUser(){
        val response = mockMvc.perform(
            MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(userToRegister)))

        val getUser = userRepository.findByEmail("enzogalarza2@gmail.com").get()

        response.andExpect(MockMvcResultMatchers.status().isOk)

        assertEquals("enzogalarza2@gmail.com",getUser.username)
        assertEquals("nombre",getUser.getName())
        assertEquals("imagen.png",getUser.getProfileImage())
    }
}