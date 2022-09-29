package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.configuration.JwtUtilService
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.LoginDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.RegisterDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.token.WebToken
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.service.ConfirmationTokenService
import ar.edu.unq.grupo7.pronosticosdeportivos.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
class AuthController {

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
        val user = User()
        user.setName(register.name)
        user.setEmail(register.email)
        user.password = passwordEncoder.encode(register.password)

        userService.registerUser(user)
    }

    @PostMapping("login")
    fun login (@RequestBody login: LoginDTO): ResponseEntity<UserDetails> {

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                login.username,
                login.password
            )
        )

        val user = userService.loadUserByUsername(login.username)

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