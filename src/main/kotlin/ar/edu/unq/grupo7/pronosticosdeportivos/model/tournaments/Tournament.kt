package ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import javax.persistence.*

@Entity
class Tournament(@Column val name : String, @Column val competition : String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name="Tournament_User",
        joinColumns=[JoinColumn(name="tournamentId")],
        inverseJoinColumns=[JoinColumn(name="userId")])
    var users : MutableList<User> = mutableListOf()

    fun addUser(user: User){
        this.users.add(user)
    }

}