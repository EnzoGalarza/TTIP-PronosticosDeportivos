package ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments

import javax.persistence.*

@Entity
class Criteria(@Column val name : String, @Column var score : Int) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
}