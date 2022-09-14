package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

data class MatchDTO(var id : Long, var status: String, var utcDate : String, val matchday: Int, var homeTeam : TeamDTO, var awayTeam : TeamDTO, var score : ScoreDTO) {

}
