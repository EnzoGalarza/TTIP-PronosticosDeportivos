package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class UserNotFoundException :
    RuntimeException(String.format(USER_NOT_FOUND)) {
    companion object {
        private const val USER_NOT_FOUND = "Usuario o contraseña incorrectos"
    }
}