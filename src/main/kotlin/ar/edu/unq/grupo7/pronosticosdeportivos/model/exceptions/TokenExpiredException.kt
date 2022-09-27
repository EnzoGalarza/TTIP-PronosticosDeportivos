package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class TokenExpiredException :
    RuntimeException(String.format(TOKEN_EXPIRED)) {
    companion object {
        private const val TOKEN_EXPIRED = "Token vencido"
    }
}