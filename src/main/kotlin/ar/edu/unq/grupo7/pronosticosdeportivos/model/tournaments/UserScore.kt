package ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import javax.persistence.*

@Entity
class UserScore(@OneToOne
                @JoinColumn(name = "user_score_id")
                val user : User, @Column var score : Int = 0) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    fun sumPoints(score: Int) {
        this.score+= score
    }
}