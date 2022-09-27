package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PronosticTestCase {

    @Test
    fun pronosticHasAUserAMatchIdALocalTeamGoalsAndAwayTeamGoals(){
        val pronostic = Pronostic("nombre_usuario",10,1,0)

        assertEquals("nombre_usuario",pronostic.user)
        assertEquals(10,pronostic.matchId)
        assertEquals(1,pronostic.localGoals)
        assertEquals(0,pronostic.awayGoals)
    }

    @Test
    fun pronosticsGive0PointsIfMatchResultIsNotEquals(){
        val pronostic = Pronostic("nombre_usuario",10,1,0)

        assertEquals(0,pronostic.checkPoints(0,2))
    }

    @Test
    fun pronosticGive3PointIfMatchResultIsEquals(){
        val pronostic = Pronostic("nombre_usuario",10,1,0)

        assertEquals(3,pronostic.checkPoints(1,0))
    }


}