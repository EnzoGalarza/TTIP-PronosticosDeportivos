package ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "current_season")
class CurrentSeason(var currentMatchday : Int?, var endDate: Date) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0

}