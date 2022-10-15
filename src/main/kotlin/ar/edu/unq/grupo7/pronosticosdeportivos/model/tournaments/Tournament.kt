package ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import javax.persistence.*

@Entity
class Tournament(@Column val name : String, @Column val competition : String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToMany(mappedBy = "tournaments")
    @Cascade(*[CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST])
    var users : MutableList<User> = mutableListOf()

    fun addUser(user: User){
        this.users.add(user)
    }

}