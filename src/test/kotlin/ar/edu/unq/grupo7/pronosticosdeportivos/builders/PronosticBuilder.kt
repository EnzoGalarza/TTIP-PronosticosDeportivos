package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import java.time.LocalDateTime

class PronosticBuilder(
    private var user : String = "x@gmail.com",
    private var match: Match = MatchBuilder().build(),
    private var localGoals : Int = 1,
    private var awayGoals : Int = 0
) {
    fun build() = Pronostic(user,match,localGoals,awayGoals)

    fun withUsername(user: String) : PronosticBuilder{
        this.user = user
        return this
    }

    fun withDate(dateTime: LocalDateTime) : PronosticBuilder{
        this.match = MatchBuilder().withDate(dateTime).build()
        return this
    }

    fun withMatch(match: Match): PronosticBuilder {
        this.match = match
        return this
    }

    fun withAwayGoals(awayGoals: Int) : PronosticBuilder{
        this.awayGoals = awayGoals
        return this
    }

    fun withLocalGoals(localGoals: Int) : PronosticBuilder{
        this.localGoals = localGoals
        return this
    }

}