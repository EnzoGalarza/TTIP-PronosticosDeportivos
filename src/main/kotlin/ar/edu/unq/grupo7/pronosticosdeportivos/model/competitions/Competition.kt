package ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions

import javax.persistence.*

@Entity
@Table(name = "competencias")
data class Competition(var name : String, var emblem : String, var code : String){

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0
}