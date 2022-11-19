package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.PronosticRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class PronosticServiceTest(@Mock val pronosticRepository: PronosticRepository, @Mock val matchService: MatchService) {

    @InjectMocks
    lateinit var pronosticService: PronosticService

    @Mock
    lateinit var pronostic : Pronostic

    @Mock
    lateinit var match : Match

    @Test
    fun saveACreatedInstance(){
        val pronosticList = listOf(pronostic)

        Mockito.`when`(pronosticRepository.findById(pronostic.id)).thenReturn(Optional.of(pronostic))
        Mockito.`when`(pronosticRepository.save(pronostic)).thenReturn(pronostic)

        pronosticService.saveAll(pronosticList)

        Mockito.verify(pronostic,times(0)).updateMatch(match)
        Mockito.verify(pronostic,times(1)).updateGoals(pronostic)

    }

    @Test
    fun saveNewInstance(){
        val pronosticList = listOf(pronostic)

        Mockito.`when`(pronosticRepository.findById(pronostic.id)).thenReturn(Optional.empty())
        Mockito.`when`(pronosticRepository.save(pronostic)).thenReturn(pronostic)
        Mockito.`when`(pronostic.match).thenReturn(match)
        Mockito.`when`(match.code).thenReturn(1)
        Mockito.`when`(matchService.getByCode(1)).thenReturn(match)

        pronosticService.saveAll(pronosticList)

        Mockito.verify(pronostic, times(1)).updateMatch(match)
        Mockito.verify(pronostic,times(0)).updateGoals(pronostic)
    }

    @Test
    fun testPronosticsFromUser(){
        Mockito.`when`(pronosticRepository.findByUserAndCompetition("pedro@gmail.com","PD"))
            .thenReturn(listOf(pronostic,pronostic))

        val pronostics = pronosticService.pronosticsFromUser("pedro@gmail.com","PD")

        assertEquals(pronostics.size, 2)
    }

    @Test
    fun testNotEvaluatedPronostics(){
        Mockito.`when`(pronosticRepository.findNotEvaluatedPronostics("pedro@gmail.com","PD",1))
            .thenReturn(listOf(pronostic))

        val notEvaluatedPronostics = pronosticService.notEvaluatedPronostics("pedro@gmail.com","PD",1)

        assertEquals(notEvaluatedPronostics.size,1)
    }


}