package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team
import java.time.LocalDateTime

class MatchBuilder(
    var homeTeam : Team = Team("hX","htX","hcX"),
    var awayTeam : Team = Team("aX","atX","acX"),
    var date : LocalDateTime = LocalDateTime.now().plusDays(2),
    var localGoals : Int? = null,
    var awayGoals : Int? = null,
    var code : Long = 3,
    var status : String = "SCHEDULED",
    var matchDay : Int = 1,
    var competition : String = "PL"
) {
    fun build() = Match(homeTeam,awayTeam,date,localGoals,awayGoals,code,status,matchDay,competition)

    fun withCode(code: Long) : MatchBuilder{
        this.code = code
        return this
    }

    fun withDate(date: LocalDateTime) : MatchBuilder{
        this.date = date
        return this
    }

    fun withDay(day : Int) : MatchBuilder{
        this.matchDay = day
        return this
    }

    fun withStatus(status: String) : MatchBuilder{
        this.status = status
        return this
    }

    fun withCompetition(competition : String) : MatchBuilder{
        this.competition = competition
        return this
    }

    fun withLocal(team : Team) : MatchBuilder{
        this.homeTeam = team
        return this
    }

    fun withAway(team : Team) :MatchBuilder{
        this.awayTeam = team
        return this
    }
}