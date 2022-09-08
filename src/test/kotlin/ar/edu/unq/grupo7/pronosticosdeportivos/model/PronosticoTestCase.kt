package ar.edu.unq.grupo7.pronosticosdeportivos.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PronosticoTestCase {

    @Test
    fun unPronosticoTieneUnUsuarioUnNumeroRepresentandoUnPartidoYUnResultadoParaElLocalYOtroParaElVisitante(){
        val pronostico = Pronostico("nombre_usuario",10,1,0)

        assertEquals("nombre_usuario",pronostico.user)
        assertEquals(10,pronostico.idPartido)
        assertEquals(1,pronostico.rlocal)
        assertEquals(0,pronostico.rvisitante)
    }

    @Test
    fun unPronosticoDaCeroPuntosSiNoEsCorrectoElResultado(){
        val pronostico = Pronostico("nombre_usuario",10,1,0)

        assertEquals(0,pronostico.resultado(0,2))
    }

    @Test
    fun unPronosticoDaTresPuntosSiEsCorrectoElResultado(){
        val pronostico = Pronostico("nombre_usuario",10,1,0)

        assertEquals(3,pronostico.resultado(1,0))
    }


}