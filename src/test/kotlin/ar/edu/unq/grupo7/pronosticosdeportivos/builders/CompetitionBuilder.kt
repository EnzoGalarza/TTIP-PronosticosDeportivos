package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition

class CompetitionBuilder(
    var name : String = "CompetitionX",
    var emblem : String = "EmblemX",
    var code : String = "cX"
) {
    fun build() = Competition(name,emblem,code)

    fun withName(name:String): CompetitionBuilder{
        this.name = name
        return this
    }

    fun withCode(code:String) : CompetitionBuilder{
        this.code = code
        return this
    }
}