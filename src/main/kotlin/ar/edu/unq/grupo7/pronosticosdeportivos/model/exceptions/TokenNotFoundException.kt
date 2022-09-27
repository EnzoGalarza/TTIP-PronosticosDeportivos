package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class TokenNotFoundException :
    RuntimeException(String.format(TOKEN_NOT_FOUND)) {
    companion object {
        private const val TOKEN_NOT_FOUND = "Token no encontrado"
    }
}