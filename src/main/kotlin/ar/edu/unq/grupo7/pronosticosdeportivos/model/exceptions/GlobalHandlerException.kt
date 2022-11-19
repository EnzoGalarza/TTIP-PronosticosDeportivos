package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ExpirationDayException::class,UsedEmailException::class,
        InvalidEmailException::class, TokenNotFoundException::class,
        InvalidPasswordException::class, InvalidNameException::class,
        EmailAlreadyConfirmedException::class, TournamentNameLengthException::class,
        DuplicateUserInTournament::class, NoCriteriaTournamentError::class,
        InvalidImageException::class)
    fun handleBadRequest(exception : Exception) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TournamentNotFoundException::class,
        MatchNotFoundException::class,
        UserNotFoundException::class, NoMatchesException::class)
    fun handleNotFound(exception : Exception) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UserDisabledException::class)
    fun handleForbidden(exception: UserDisabledException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(EmailSendErrorException::class)
    fun handleServerError(exception : EmailSendErrorException) : ResponseEntity<Any>{
        return ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR)
    }

}