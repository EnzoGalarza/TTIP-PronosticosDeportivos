package ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments

import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.*
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import javax.persistence.*

@Entity
class Tournament(@Column val name : String,
                 @Column val competition : String,
                 @OneToMany(cascade = [CascadeType.ALL])
                 @JoinColumn(name = "tournamentId")
                 val criterias : List<Criteria>) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name="Tournament_User",
        joinColumns=[JoinColumn(name="tournamentId")],
        inverseJoinColumns=[JoinColumn(name="userScoreId")])
    var users : MutableList<UserScore> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "tournamentId")
    var evaluatedPronostic : MutableList<EvaluatedPronostic> = mutableListOf()

    fun addUser(user: User){
        this.users.add(UserScore(user,0))
    }

    fun getCriteria(criteria : String) : PronosticCriteria{
        var pronosticCriteria : PronosticCriteria? = null
        when(criteria){
            "Complete" -> pronosticCriteria = CompleteResult
            "Partial" -> pronosticCriteria = PartialResult
            "Approach" -> pronosticCriteria = Approach
            "WinnerOrTie" -> pronosticCriteria = WinnerOrTie
        }
        return pronosticCriteria!!
    }

    fun addEvaluatedPronostic(id: Long) {
        this.evaluatedPronostic.add(EvaluatedPronostic(id))
    }

}