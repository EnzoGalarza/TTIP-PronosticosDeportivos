package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class UsedEmailException(email: String?) :
    RuntimeException(String.format(USED_EMAIL, email)) {
    companion object {
        private const val USED_EMAIL = "El email %s ya fué utilizado"
    }
}