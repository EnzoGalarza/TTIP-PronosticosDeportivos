package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.CompetitionBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.configuration.ApiConfiguration
import ar.edu.unq.grupo7.pronosticosdeportivos.configuration.CorsConfig
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import ar.edu.unq.grupo7.pronosticosdeportivos.service.CompetitionService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockServletContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ApiConfiguration::class, CorsConfig::class, CompetitionController::class])
@WebAppConfiguration
class CompetitionControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var competitionController: CompetitionController

    @MockBean
    private lateinit var competitionService: CompetitionService

    private lateinit var competition: Competition

    private lateinit var competition2 : Competition

    private val competitionBuilder = CompetitionBuilder()

    private var mockMvc: MockMvc? = null

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        competition = competitionBuilder.withName("competition 1").build()
        competition2 = competitionBuilder.withName("competition 2").build()
        mockMvc = MockMvcBuilders.standaloneSetup(competitionController).build()
    }

    @Test
    fun servletContext() {
        val servletContext = webApplicationContext.servletContext
        assertNotNull(servletContext)
        assertTrue(servletContext is MockServletContext)
        assertNotNull(webApplicationContext.getBean("restTemplate"))
        assertNotNull(webApplicationContext.getBean("addCorsConfig"))
        assertNotNull(webApplicationContext.getBean("competitionController"))
    }

    @Test
    fun getCompetitions() {
        val expectingList = mutableListOf(competition,competition2)

        `when`(competitionService.getCompetitions()).thenReturn(expectingList)


        mockMvc!!.perform(get("/competitions")).andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("competition 1"))
            .andExpect(jsonPath("$[1].name").value("competition 2"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

    }

    @Test
    fun getCurrentMatchDay(){
        `when`(competitionService.getCurrentMatchDay(competition.name)).thenReturn(3)

        mockMvc!!.perform(get("/currentMatchDay/{competition}",competition.name))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").value(3))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

    }
}