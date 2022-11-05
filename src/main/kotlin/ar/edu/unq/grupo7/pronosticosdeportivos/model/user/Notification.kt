package ar.edu.unq.grupo7.pronosticosdeportivos.model.user

import javax.persistence.*

@Entity
class Notification(@Column var acceptable : Boolean, @Column var message : String, @Column var tournamentId : Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

}