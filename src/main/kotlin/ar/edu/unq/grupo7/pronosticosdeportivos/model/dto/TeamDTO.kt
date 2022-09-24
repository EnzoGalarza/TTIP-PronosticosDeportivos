package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Team

data class TeamDTO(val name: String?, val tla: String?, val crest : String?) {

}

fun TeamDTO.toModel() = Team(name = name!!,tla = tla,crest = crest)
