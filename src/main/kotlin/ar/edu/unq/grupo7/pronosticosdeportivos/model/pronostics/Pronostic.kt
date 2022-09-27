package ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics

import javax.persistence.*

@Entity
@Table(name = "pronosticos")
data class Pronostic(@Column val user: String, @Column val matchId: Int,
                     @Column var localGoals: Int, @Column var awayGoals: Int){


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0

    fun checkPoints(localGoals:Int, awayGoals:Int) : Int{
        if(localGoals == this.localGoals && awayGoals == this.awayGoals)
            return 3
        return 0
    }

    fun updateGoals(pronostic: Pronostic){
        this.localGoals = pronostic.localGoals
        this.awayGoals = pronostic.awayGoals
    }
}
