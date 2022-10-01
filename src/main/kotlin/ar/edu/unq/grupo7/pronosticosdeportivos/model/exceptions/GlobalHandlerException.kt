package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ExpirationDayException::class)
    fun expirationDayException(exception: ExpirationDayException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UsedEmailException::class)
    fun usedEmailException(exception: UsedEmailException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidEmailException::class)
    fun invalidEmailException(exception: InvalidEmailException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TokenNotFoundException::class)
    fun tokenNotFoundException(exception: TokenNotFoundException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserDisabledException::class)
    fun tokenNotFoundException(exception: UserDisabledException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun invalidPasswordException(exception: InvalidPasswordException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundException(exception: UserNotFoundException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

}