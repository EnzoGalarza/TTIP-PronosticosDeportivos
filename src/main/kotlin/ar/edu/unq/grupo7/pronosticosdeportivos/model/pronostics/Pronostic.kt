package ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.ExpirationDayException
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "pronostic")
data class Pronostic(@Column val user: String,
                     @OneToOne
                     @Cascade(CascadeType.SAVE_UPDATE, CascadeType.DETACH, CascadeType.MERGE)
                     @JoinColumn(name = "matchId",referencedColumnName = "id") var match: Match,
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

    fun validate(){
        require(!match.date!!.isBefore(LocalDateTime.now())){
            throw ExpirationDayException("El partido ya empezó")
        }
    }
}
