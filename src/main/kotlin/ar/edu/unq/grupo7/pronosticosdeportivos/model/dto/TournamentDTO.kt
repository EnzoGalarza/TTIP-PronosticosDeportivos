package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import java.time.LocalDate

data class TournamentDTO(val name : String, val competition : String, val creator : String, val usersEmail : List<String>,
                         val criteria : List<Criteria>)

fun TournamentDTO.toModel(competitionEndDate: LocalDate) = Tournament(name = name, competition = competition, criterias = criteria, endDate = competitionEndDate)