package ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import javax.persistence.*

@Entity
@Table(name = "pronosticos")
data class Pronostic(@Column val user: String,
                     @OneToOne(cascade = [CascadeType.ALL])
                     @JoinColumn(name = "code",referencedColumnName = "code") var match: Match,
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

    fun updateMatch(match: Match){
        this.match = match
    }
}
