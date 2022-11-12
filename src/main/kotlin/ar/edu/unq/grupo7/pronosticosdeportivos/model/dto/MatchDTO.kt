package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class MatchDTO(var id : Long, var status: String, var utcDate : LocalDateTime, val matchday: Int, var homeTeam : TeamDTO, var awayTeam : TeamDTO, var score : ScoreDTO, var localDate : String = "", var weekDay : DayOfWeek? = null) {

    init {
        val offSet = utcDate.atZone(ZoneId.systemDefault()).offset
        localDate = utcDate.atOffset(ZoneOffset.UTC).withOffsetSameInstant(offSet).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        weekDay = utcDate.atOffset(ZoneOffset.UTC).withOffsetSameInstant(offSet).dayOfWeek
    }
}

fun MatchDTO.toModel(competition : String, localTeam : Team, awayTeam: Team) = Match(homeTeam = localTeam, awayTeam = awayTeam,date = utcDate,
    localGoals = score.fullTime.home, awayGoals = score.fullTime.away, code = id,status = status, matchDay = matchday, competition = competition)
