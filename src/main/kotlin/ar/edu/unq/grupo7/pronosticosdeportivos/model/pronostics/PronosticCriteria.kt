package ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import kotlin.math.absoluteValue

interface PronosticCriteria {
    fun eval(pronostic: Pronostic, match : Match) : Boolean
}

object CompleteResult : PronosticCriteria{
    override fun eval(
        pronostic: Pronostic,
        match : Match
    ): Boolean {
        return pronostic.localGoals == match.localGoals!! && pronostic.awayGoals == match.awayGoals!!
    }

}

object PartialResult : PronosticCriteria{
    override fun eval(
        pronostic: Pronostic,
        match : Match
    ): Boolean {
        return pronostic.localGoals == match.localGoals!! || pronostic.awayGoals == match.awayGoals!!
    }
}

object WinnerOrTie : PronosticCriteria{
    override fun eval(
        pronostic: Pronostic,
        match : Match
    ): Boolean {
        val winnerLocal = pronostic.localGoals > pronostic.awayGoals && match.localGoals!! > match.awayGoals!!
        val winnerAway = pronostic.localGoals < pronostic.awayGoals && match.localGoals!! < match.awayGoals!!
        val tie = pronostic.localGoals == pronostic.awayGoals && match.localGoals!! == match.awayGoals!!
        return winnerLocal || winnerAway || tie
    }

}

object Approach : PronosticCriteria{
    override fun eval(
        pronostic: Pronostic,
        match : Match
    ): Boolean {
        val localDifference = (pronostic.localGoals - match.localGoals!!).absoluteValue
        val awayDifference = (pronostic.awayGoals - match.awayGoals!!).absoluteValue

        return localDifference <= 2 && awayDifference <= 2
    }

}