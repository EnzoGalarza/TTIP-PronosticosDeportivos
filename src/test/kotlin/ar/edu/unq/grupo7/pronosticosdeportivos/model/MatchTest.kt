package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class MatchTest {

    @Mock lateinit var homeTeam : Team

    @Mock lateinit var awayTeam : Team

    @Test
    fun AMatchHasAHomeTeamAnAwayTeamADateAnLocalGoalsAnAwayGoalsACodeAStatusAMatchDayAndCompetitionName(){
        val date = LocalDateTime.now()
        val match = Match(homeTeam,awayTeam,date ,1,2,1,"FINISHED",3,"PD")

        assertEquals(match.homeTeam,homeTeam)
        assertEquals(match.awayTeam,awayTeam)
        assertEquals(match.date,date)
        assertEquals(match.localGoals,1)
        assertEquals(match.awayGoals,2)
        assertEquals(match.code,1)
        assertEquals(match.status,"FINISHED")
        assertEquals(match.matchDay,3)
        assertEquals(match.competition,"PD")
    }
}