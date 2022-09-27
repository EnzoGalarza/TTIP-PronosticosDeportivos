package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class EmailAlreadyConfirmedException :
    RuntimeException(String.format(ALREADY_CONFIRMED)) {
    companion object {
        private const val ALREADY_CONFIRMED = "El token ya fué confirmado"
    }
}