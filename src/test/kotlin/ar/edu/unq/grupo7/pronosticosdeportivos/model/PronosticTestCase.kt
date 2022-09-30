package ar.edu.unq.grupo7.pronosticosdeportivos.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PronosticTestCase {

    @Mock
    private lateinit var match : Match

    @Test
    fun pronosticHasAUserAMatchALocalTeamGoalsAndAwayTeamGoals(){
        val pronostic = Pronostic("nombre_usuario",match,1,0)

        assertEquals("nombre_usuario",pronostic.user)
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


}