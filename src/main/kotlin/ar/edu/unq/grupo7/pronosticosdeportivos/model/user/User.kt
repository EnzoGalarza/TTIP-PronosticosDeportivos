package ar.edu.unq.grupo7.pronosticosdeportivos.model.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
class User: UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    private var name = ""

    @Column(unique = true)
    private var email: String = ""

    @Column
    private var password: String = ""

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

    fun setName(name: String){
        this.name = name
    }

    override fun getUsername(): String{
        return this.email
    }

    fun getName(): String{
        return this.name
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun setPassword(pass: String) {
        val passwordEcncoder = BCryptPasswordEncoder()
        this.password = passwordEcncoder.encode(pass)
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
}

/*class AppUser : UserDetails {
    //Methods
    //Parameters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @NotEmpty
    var name: String? = null

    @NotEmpty
    private var email: String? = null

    @NotEmpty
    private var password: String? = null
    var accountCredit = 0.0
        private set
    var accountExpense = 0.0
        private set

    @Enumerated(EnumType.STRING)
    private var role: UserRole? = null
    private var expired: Boolean? = null
    private var locked: Boolean? = null
    private var enabled: Boolean? = null

    //Constructor
    constructor() {}
    constructor(name: String?, email: String, password: String?) {
        isAValidEmail(email)
        this.name = name
        this.email = email
        this.password = password
        accountCredit = 0.0
        accountExpense = 0.0
        role = USER
        expired = false
        locked = false
        enabled = false
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    private fun isAValidEmail(newEmail: String) {
        if (!newEmail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) throw InvalidEmailException(newEmail)
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val authority = SimpleGrantedAuthority(role.name())
        return listOf(authority)
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return !expired!!
    }

    override fun isAccountNonLocked(): Boolean {
        return !locked!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled!!
    }
}*/
