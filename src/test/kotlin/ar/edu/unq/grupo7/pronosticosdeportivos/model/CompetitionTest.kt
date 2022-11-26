package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class CompetitionTest {


    @Test
    fun aCompetitionHasANameACodeAEmblemACurrentMatchDayAndEndDate(){
        val date = LocalDate.now().plusDays(2)
        val competition = Competition("Campeonato Brasileiro Série A",
        "https://crests.football-data.org/764.svg","BSA", 1, date)

        assertEquals(competition.name,"Campeonato Brasileiro Série A")
        assertEquals(competition.code,"BSA")
        assertEquals(competition.emblem,"https://crests.football-data.org/764.svg")
        assertEquals(competition.currentMatchday,1)
        assertEquals(competition.endDate, date)
    }
}