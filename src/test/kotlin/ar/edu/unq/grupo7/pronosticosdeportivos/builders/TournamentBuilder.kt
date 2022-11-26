package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import java.time.LocalDate

class TournamentBuilder(
    var name : String = "tournament name",
    var competition : String = "PD",
    var criterias : List<Criteria> = listOf(Criteria("Complete",1)),
    val endDate : LocalDate = LocalDate.now().plusDays(2)
) {
    fun build() : Tournament = Tournament(name,competition,criterias, endDate)

    fun withName(name : String) : TournamentBuilder{
        this.name = name
        return this
    }

    fun withCompetition(competition: String) : TournamentBuilder{
        this.competition = competition
        return this
    }

    fun withCriterias(criterias : List<Criteria>) : TournamentBuilder{
        this.criterias = criterias
        return this
    }
}