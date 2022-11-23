package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.UserScore

class TournamentResultsDTO(val users: List<UserScore>, val finished: Boolean) {
}