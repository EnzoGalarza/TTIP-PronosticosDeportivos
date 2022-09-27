package ar.edu.unq.grupo7.pronosticosdeportivos.model.validations

import org.springframework.stereotype.Service
import java.util.function.Predicate

@Service
class EmailValidator : Predicate<String> {
    override fun test(value: String): Boolean {
        return value.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}".toRegex())
    }
}