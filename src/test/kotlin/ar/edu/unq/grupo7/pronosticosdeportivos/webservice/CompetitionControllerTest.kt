package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import ar.edu.unq.grupo7.pronosticosdeportivos.service.CompetitionService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class CompetitionControllerTest(@Mock val competitionService: CompetitionService) {

    @InjectMocks
    lateinit var competitionController: CompetitionController

    @Mock lateinit var competition: Competition

    @Mock lateinit var competition2 : Competition

    @Test
    fun getCompetition(){
        Mockito.`when`(competitionService.getCompetitions()).thenReturn(mutableListOf(competition,competition2))

        val response = competitionController.getCompetitions()

        assertEquals(response.body, mutableListOf(competition,competition2))
        assertEquals(response.statusCode,HttpStatus.OK)
    }

    @Test
    fun getCurrentMatchDay(){
        Mockito.`when`(competition.code).thenReturn("PD")
        Mockito.`when`(competitionService.getCurrentMatchDay("PD")).thenReturn(3)

        val response = competitionController.getCurrentMatchDay(competition.code)

        assertEquals(response.body,3)
        assertEquals(response.statusCode,HttpStatus.OK)
    }
}