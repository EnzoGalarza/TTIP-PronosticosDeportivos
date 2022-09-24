package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Match
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class MatchDTO(var id : Long, var status: String, var utcDate : LocalDateTime, val matchday: Int, var homeTeam : TeamDTO, var awayTeam : TeamDTO, var score : ScoreDTO, var localDate : String = "") {

    init {
        val offSet = utcDate.atZone(ZoneId.systemDefault()).offset
        localDate = utcDate.atOffset(ZoneOffset.UTC).withOffsetSameInstant(offSet).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
    }
}

fun MatchDTO.toModel(competition : String) = Match(teams = listOf(homeTeam.toModel(),awayTeam.toModel()),date = utcDate,
    localGoals = score.fullTime.home, awayGoals = score.fullTime.away, code = id,status = status, matchDay = matchday, competition = competition)
