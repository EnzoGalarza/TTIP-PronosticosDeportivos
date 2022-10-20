package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.PronosticBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PronosticService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [PronosticController::class])
@WebAppConfiguration
class PronosticControllerTest {

    @Autowired
    lateinit var pronosticController: PronosticController

    @MockBean
    lateinit var pronosticService : PronosticService

    private var mockMvc: MockMvc? = null
    private val mapper = ObjectMapper()

    private val pronosticBuilder = PronosticBuilder()
    private lateinit var pronostic1 : Pronostic
    private lateinit var pronostic2 : Pronostic

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        pronostic1 = pronosticBuilder.withUsername("jose").build()
        pronostic2 = pronosticBuilder.withUsername("pedro").build()
        mockMvc = MockMvcBuilders.standaloneSetup(pronosticController).build()
        mapper.registerModule(JavaTimeModule())
    }


    @Test
    fun registerPronostics(){
        val pronosticsToSave = mutableListOf(pronostic1,pronostic2)

        `when`(pronosticService.saveAll(pronosticsToSave)).thenReturn(mutableListOf(pronostic1,pronostic2))

        mockMvc!!.perform(post("/pronostics")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(pronosticsToSave)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].user").value("jose"))
            .andExpect(jsonPath("$[1].user").value("pedro"))
    }

    @Test
    fun getAllPronosticsFromUser(){
        `when`(pronosticService.pronosticsFromUser("jose","PD")).thenReturn(listOf(pronostic1))

        mockMvc!!.perform(get("/pronostics/{user}/{competition}","jose","PD"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].user").value("jose"))
    }

}