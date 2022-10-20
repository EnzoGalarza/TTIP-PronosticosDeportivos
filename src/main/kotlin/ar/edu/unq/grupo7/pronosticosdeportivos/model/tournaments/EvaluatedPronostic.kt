package ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments

import javax.persistence.*

@Entity
class EvaluatedPronostic(@Column val pronosticId : Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}