package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import java.time.LocalDate

class CompetitionBuilder(
    var name : String = "CompetitionX",
    var emblem : String = "EmblemX",
    var code : String = "cX",
    val currentMatchDay: Int = 1,
    val endDate: LocalDate = LocalDate.now().plusDays(2)
) {
    fun build() = Competition(name,emblem,code, currentMatchDay,endDate)

    fun withName(name:String): CompetitionBuilder{
        this.name = name
        return this
    }

    fun withCode(code:String) : CompetitionBuilder{
        this.code = code
        return this
    }
}