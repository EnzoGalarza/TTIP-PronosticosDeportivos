package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.RegisterDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.service.ConfirmationTokenService
import ar.edu.unq.grupo7.pronosticosdeportivos.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class AuthController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var confirmationTokenService: ConfirmationTokenService

    @PostMapping("register")
    fun register(@RequestBody register: RegisterDTO){
        val user = User()
        user.setName(register.name)
        user.setEmail(register.email)
        user.password = register.password

        userService.registerUser(user)
    }

    @GetMapping(path = ["confirmToken"])
    fun confirm(@RequestParam("token") token: String): String? {
        return confirmationTokenService.confirmToken(token)
    }

}