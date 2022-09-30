package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TeamDTO
import javax.persistence.*

@Entity
@Table(name = "equipos")
class Team(val name: String?, val tla : String?, val crest : String?) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0


}

fun Team.toDTO() = TeamDTO(name = name, tla = tla, crest = crest)