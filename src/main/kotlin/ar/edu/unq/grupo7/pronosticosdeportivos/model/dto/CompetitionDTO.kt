package ar.edu.unq.grupo7.pronosticosdeportivos.model.dto

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition

class CompetitionDTO(var name : String, var emblem : String, var code : String, var currentSeason : CurrentSeasonDTO) {
}

fun CompetitionDTO.toModel() = Competition(name = name, emblem = emblem, code = code, currentMatchday = currentSeason.currentMatchday,
                                           endDate = currentSeason.endDate)

