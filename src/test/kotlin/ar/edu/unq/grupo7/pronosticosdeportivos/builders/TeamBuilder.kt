package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team

class TeamBuilder(
    var name : String = "Team n",
    var tla : String = "NT",
    var crest : String = "crest team n"
) {
    fun build() = Team(name,tla,crest)

    fun withName(name : String) : TeamBuilder{
        this.name = name
        return this
    }
}