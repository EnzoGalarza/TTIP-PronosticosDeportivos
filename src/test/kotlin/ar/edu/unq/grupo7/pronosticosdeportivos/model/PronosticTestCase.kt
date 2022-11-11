package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.PronosticBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.ExpirationDayException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class PronosticTestCase {

    @Mock
    private lateinit var match : Match

    private val pronosticBuilder = PronosticBuilder()

    @Test
    fun pronosticHasAUserAMatchALocalTeamGoalsAndAwayTeamGoals(){
        val pronostic = Pronostic("nombre_usuario",match,1,0)

        assertEquals("nombre_usuario",pronostic.user)
        assertEquals(match,pronostic.match)
        assertEquals(1,pronostic.localGoals)
        assertEquals(0,pronostic.awayGoals)
    }

    @Test
    fun updateGoalsFromPronostic(){
        val pronostic = pronosticBuilder.withLocalGoals(2).withAwayGoals(1).build()

        val oldLocalGoals = pronostic.localGoals
        val oldAwayGoals = pronostic.awayGoals

        pronostic.updateGoals(pronosticBuilder.withLocalGoals(3).withAwayGoals(4).build())

        assertEquals(pronostic.localGoals,3)
        assertEquals(pronostic.awayGoals,4)
        assertEquals(oldLocalGoals,2)
        assertEquals(oldAwayGoals,1)
    }

    @Test
    fun pronosticValidateShouldFailWithExpirationDayExceptionIfMatchHasEnded(){
        Mockito.`when`(match.date).thenReturn(LocalDateTime.now().minusDays(2))
        val pronostic = Pronostic("nombre_usuario",match,1,0)

        assertThrows(ExpirationDayException::class.java){
            pronostic.validate()
        }
    }


}