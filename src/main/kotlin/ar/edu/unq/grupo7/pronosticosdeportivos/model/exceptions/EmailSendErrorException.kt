package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
class EmailSendErrorException(e: Exception):
    RuntimeException(String.format(EMAIL_SEND_ERROR, e)) {
        companion object {
            private const val EMAIL_SEND_ERROR = "Error en el envio de email: %s"
        }
}