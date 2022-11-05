package ar.edu.unq.grupo7.pronosticosdeportivos.model.user

import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.InvalidNameException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*


@Entity
class User: UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    private var name: String = ""

    @Column(unique = true)
    private var email: String = ""

    @Column
    private var password: String = ""

    @Lob
    private var profileImage: String = ""

    @Column
    private var enabled: Boolean = true

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    private var notifacations : MutableList<Notification> = mutableListOf()

    @Enumerated(EnumType.STRING)
    private val role: UserRole = UserRole.USER

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val authority = SimpleGrantedAuthority(role.name)
        return listOf(authority)
    }

    override fun getPassword(): String {
        return password
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
        return enabled
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

    fun validate() {
        require(name.length > 3){
            throw InvalidNameException("El nombre del usuario debe tener por lo menos 3 caracteres")
        }
    }

    fun addNotification(notification: Notification) {
        this.notifacations.add(notification)
    }

    fun deleteNotification(notification: Notification) {
        this.notifacations.remove(notification)
    }

}
