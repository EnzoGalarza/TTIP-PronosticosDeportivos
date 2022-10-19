package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.MatchBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.toDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.service.MatchService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [MatchController::class])
@WebAppConfiguration
class MatchControllerTest {

    @Autowired
    lateinit var matchController : MatchController

    @MockBean
    lateinit var matchService : MatchService

    private var mockMvc: MockMvc? = null

    private val matchBuilder : MatchBuilder = MatchBuilder()

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build()
    }

    @Test
    fun getMatchesOfCompetitionPL(){
        val match1 = matchBuilder.withCode(1).build()
        val match2 = matchBuilder.withCode(5).build()

        `when`(matchService.getMatches("PL",1)).thenReturn(mutableListOf(match1.toDTO(),match2.toDTO()))
        mockMvc!!.perform(get("/matches/{competition}/{matchDay}","PL",1))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(5))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}