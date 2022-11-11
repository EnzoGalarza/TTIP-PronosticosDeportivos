package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament

class TournamentBuilder(
    var name : String = "tournament name",
    var competition : String = "PD",
    var criterias : List<Criteria> = listOf(Criteria("Complete",1))
) {
    fun build() : Tournament = Tournament(name,competition,criterias)

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