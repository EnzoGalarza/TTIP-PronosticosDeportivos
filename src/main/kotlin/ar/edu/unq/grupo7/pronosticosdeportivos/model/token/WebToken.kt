package ar.edu.unq.grupo7.pronosticosdeportivos.model.token

import java.io.Serializable

class WebToken(val jwtToken: String) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}