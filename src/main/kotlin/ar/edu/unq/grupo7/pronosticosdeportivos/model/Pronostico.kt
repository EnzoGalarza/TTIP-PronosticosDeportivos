package ar.edu.unq.grupo7.pronosticosdeportivos.model

import javax.persistence.*
import kotlin.math.max

@Entity
@Table(name = "pronosticos")
data class Pronostico(@Column val user: String, @Column val idPartido: Int,
                 @Column val rlocal: Int, @Column val rvisitante: Int){


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0

    fun resultado(local:Int,visitante:Int) : Int{
        if(local == rlocal && visitante == rvisitante)
            return 3
        return 0
    }
}
