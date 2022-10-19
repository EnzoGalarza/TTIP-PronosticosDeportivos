package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import java.time.LocalDateTime

class PronosticBuilder(
    private var user : String = "x@gmail.com",
    private var match: Match = MatchBuilder().build(),
    private val localGoals : Int = 1,
    private val awayGoals : Int = 0
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

}