package ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "competition")
data class Competition(var name : String,
                       var emblem : String,
                       var code : String,
                       var currentMatchday : Int,
                       var endDate: LocalDate) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0
}