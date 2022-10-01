package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class TeamTest {

    @Test
    fun aTeamHasANameAShortedNameAndACrest(){
        val team = Team("UD Almería","ALM","https://crests.football-data.org/267.png")

        assertEquals(team.name,"UD Almería")
        assertEquals(team.tla,"ALM")
        assertEquals(team.crest,"https://crests.football-data.org/267.png")
    }
}