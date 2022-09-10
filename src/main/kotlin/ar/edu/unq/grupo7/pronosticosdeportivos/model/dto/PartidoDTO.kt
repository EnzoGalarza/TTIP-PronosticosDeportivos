package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

data class PartidoDTO(var status: String, var utcDate : String, val matchday: Int, var homeTeam : EquipoDTO, var awayTeam : EquipoDTO, var score : ScoreDTO) {

}
