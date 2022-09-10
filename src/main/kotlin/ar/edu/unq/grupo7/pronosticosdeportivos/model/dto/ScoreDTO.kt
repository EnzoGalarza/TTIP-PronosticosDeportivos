package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

data class ScoreDTO(var winner : String?,var fullTime : ResultDTO){
}

data class ResultDTO(var home : Int?, var away : Int?)
