package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.ExpirationDayException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
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

    @BeforeEach
    fun setUp(){
        Mockito.`when`(match.date).thenReturn(LocalDateTime.now().plusDays(2))
    }

    @Test
    fun pronosticHasAUserAMatchALocalTeamGoalsAndAwayTeamGoals(){
        val pronostic = Pronostic("nombre_usuario",match,1,0)

        assertEquals("nombre_usuario",pronostic.user)
        assertEquals(match,pronostic.match)
        assertEquals(1,pronostic.localGoals)
        assertEquals(0,pronostic.awayGoals)
    }

    @Test
    fun pronosticsGive0PointsIfMatchResultIsNotEquals(){
        val pronostic = Pronostic("nombre_usuario",match,1,0)

        assertEquals(0,pronostic.checkPoints(0,2))
    }

    @Test
    fun pronosticGive3PointIfMatchResultIsEquals(){
        val pronostic = Pronostic("nombre_usuario",match,1,0)

        assertEquals(3,pronostic.checkPoints(1,0))
    }

    @Test
    fun pronosticShouldFailWithExpirationDayExceptionIfMatchHasEnded(){
        Mockito.`when`(match.date).thenReturn(LocalDateTime.now().minusDays(2))

        assertThrows(ExpirationDayException::class.java){
            val pronostic = Pronostic("nombre_usuario",match,1,0)
        }
    }


}