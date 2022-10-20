package ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics

import kotlin.math.absoluteValue

interface PronosticCriteria {
    fun eval(predictionLocalGoals : Int,predictionAwayGoals : Int, matchLocalGoals : Int, matchAwayGoals : Int) : Boolean
}

object CompleteResult : PronosticCriteria{
    override fun eval(
        predictionLocalGoals: Int,
        predictionAwayGoals: Int,
        matchLocalGoals: Int,
        matchAwayGoals: Int
    ): Boolean {
        return predictionLocalGoals == matchLocalGoals && predictionAwayGoals == matchAwayGoals
    }

}

object PartialResult : PronosticCriteria{
    override fun eval(
        predictionLocalGoals: Int,
        predictionAwayGoals: Int,
        matchLocalGoals: Int,
        matchAwayGoals: Int
    ): Boolean {
        return predictionLocalGoals == matchLocalGoals || predictionAwayGoals == matchAwayGoals
    }
}

object WinnerOrTie : PronosticCriteria{
    override fun eval(
        predictionLocalGoals: Int,
        predictionAwayGoals: Int,
        matchLocalGoals: Int,
        matchAwayGoals: Int
    ): Boolean {
        val winnerLocal = predictionLocalGoals > predictionAwayGoals && matchLocalGoals > matchAwayGoals
        val winnerAway = predictionLocalGoals < predictionAwayGoals && matchLocalGoals < matchAwayGoals
        val tie = predictionLocalGoals == predictionAwayGoals && matchLocalGoals == matchAwayGoals
        return winnerLocal || winnerAway || tie
    }

}

object Approach : PronosticCriteria{
    override fun eval(
        predictionLocalGoals: Int,
        predictionAwayGoals: Int,
        matchLocalGoals: Int,
        matchAwayGoals: Int
    ): Boolean {
        val localDifference = (predictionLocalGoals - matchLocalGoals).absoluteValue
        val awayDifference = (predictionAwayGoals - matchAwayGoals).absoluteValue

        return localDifference <= 2 && awayDifference <= 2
    }

}