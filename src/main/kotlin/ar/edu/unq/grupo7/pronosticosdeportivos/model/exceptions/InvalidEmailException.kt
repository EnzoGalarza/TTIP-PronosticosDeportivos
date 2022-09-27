package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class InvalidEmailException(email: String?) :
    RuntimeException(String.format(NOT_VALID_EMAIL, email)) {
    companion object {
        private const val NOT_VALID_EMAIL = "El email %s no es válido"
    }
}