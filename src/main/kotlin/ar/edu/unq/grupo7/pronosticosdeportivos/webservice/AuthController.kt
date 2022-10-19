package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.configuration.JwtUtilService
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.LoginDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.RegisterDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.InvalidEmailException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.InvalidPasswordException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UserDisabledException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UserNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.token.WebToken
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.model.validations.EmailValidator
import ar.edu.unq.grupo7.pronosticosdeportivos.service.ConfirmationTokenService
import ar.edu.unq.grupo7.pronosticosdeportivos.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
class AuthController {

    private val emailValidator: EmailValidator = EmailValidator()

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var confirmationTokenService: ConfirmationTokenService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtilService: JwtUtilService

    val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping("register")
    fun register(@RequestBody register: RegisterDTO){
        val isValidEmail: Boolean = this.emailValidator.test(register.email)
        if (!isValidEmail) {
            throw InvalidEmailException("El email ${register.email} no es válido")
        }
        require(register.password.length >= 3) {
            throw InvalidPasswordException("La contraseña debe tener al menos 3 caracteres")
        }
        val user = User()
        user.setName(register.name)
        user.setEmail(register.email)
        user.password = passwordEncoder.encode(register.password)
        user.setProfileImage(register.image)

        userService.signUpUser(user)
    }

    @PostMapping("login")
    fun login (@RequestBody login: LoginDTO): ResponseEntity<User> {
        try{
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    login.username,
                    login.password
                )
             )
        }
        catch (e: DisabledException){
            throw UserDisabledException("Falta validar cuenta de email")
        }
        catch (e : Exception){
            throw UserNotFoundException("Usuario o contraseña incorrectos")
        }

        val user: User = userService.loadUserByUsername(login.username)

        var jwt: String = jwtUtilService.generateToken(user);

        var webToken = WebToken(jwt)

        val responseHeaders = HttpHeaders()
        responseHeaders.set(
            "Authentication",
            webToken.jwtToken
        )

        return ResponseEntity.ok()
            .headers(responseHeaders)
            .body(user);
    }

    @GetMapping(path = ["confirmToken"])
    fun confirm(@RequestParam("token") token: String): String? {
        return confirmationTokenService.confirmToken(token)
    }
}