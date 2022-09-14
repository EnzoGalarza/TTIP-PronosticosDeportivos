package ar.edu.unq.grupo7.pronosticosdeportivos.model

import javax.persistence.*

@Entity
@Table(name = "pronosticos")
data class Pronostic(@Column val user: String, @Column val matchId: Int,
                     @Column val localGoals: Int, @Column val awayGoals: Int){


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0

    fun checkPoints(localGoals:Int, awayGoals:Int) : Int{
        if(localGoals == this.localGoals && awayGoals == this.awayGoals)
            return 3
        return 0
    }
}
