package ar.edu.unq.grupo7.pronosticosdeportivos.model.user

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
class User: UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToMany
    @JoinColumn(name = "user_id")
    @Cascade(*[CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST])
    @JsonIgnore
    val tournaments : MutableList<Tournament> = mutableListOf()

    @Column
    private var name = ""

    @Column(unique = true)
    private var email: String = ""

    @Column
    private var password: String = ""

    @Column
    private var profileImage: String = ""

    @Column
    private var enabled: Boolean = false

    @Enumerated(EnumType.STRING)
    private val role: UserRole = UserRole.USER

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val authority = SimpleGrantedAuthority(role.name)
        return listOf(authority)
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String{
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled!!
    }

    fun getName(): String{
        return this.name
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun setPassword(pass: String) {
        this.password = pass
    }

    fun setName(name: String){
        this.name = name
    }

    fun setProfileImage(image: String){
        this.profileImage = image
    }

    fun getProfileImage(): String{
        return this.profileImage
    }

    fun addTournament(tournament: Tournament){
        tournaments.add(tournament)
        tournament.addUser(this)
    }

}
