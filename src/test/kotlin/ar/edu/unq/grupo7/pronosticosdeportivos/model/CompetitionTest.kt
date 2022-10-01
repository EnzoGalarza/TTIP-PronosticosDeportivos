package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CompetitionTest {


    @Test
    fun aCompetitionHasANameACodeAndAEmblem(){
        val competition = Competition("Campeonato Brasileiro Série A",
        "https://crests.football-data.org/764.svg","BSA")

        assertEquals(competition.name,"Campeonato Brasileiro Série A")
        assertEquals(competition.code,"BSA")
        assertEquals(competition.emblem,"https://crests.football-data.org/764.svg")
    }
}