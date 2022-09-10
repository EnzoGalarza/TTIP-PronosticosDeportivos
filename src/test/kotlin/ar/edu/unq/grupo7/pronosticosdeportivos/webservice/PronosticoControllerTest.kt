package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostico
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PronosticoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class PronosticoControllerTest(@Mock val pronosticoService : PronosticoService) {

    @InjectMocks
    lateinit var pronosticoController: PronosticoController

    @Test
    fun registrarPronostico(){
        val pronostico = Pronostico("pedro",33,2,2)

        Mockito.`when`(pronosticoService.save(pronostico)).thenReturn(pronostico)

        val pronosticoObtenido = pronosticoController.registrarPronostico(pronostico)

        assertEquals(pronosticoObtenido.body,pronostico)
        assertEquals(pronosticoObtenido.statusCode,HttpStatus.CREATED)
    }

    @Test
    fun obtenerPronosticosDeUnUsuario(){
        val pronostico1 = Pronostico("jose",34,2,1)
        val pronostico2 = Pronostico("jose",35,2,2)
        val pronostico3 = Pronostico("pedro",34,2,1)

        Mockito.`when`(pronosticoService.pronosticosDeUsuario("jose")).thenReturn(listOf(pronostico1,pronostico2))

        val todosLosPronosticos = listOf(pronostico1,pronostico2,pronostico3)
        val pronosticosDeJose = pronosticoController.getPronosticosDeUsuario("jose")

        assertEquals(2,pronosticosDeJose.size)
        assertEquals(listOf(pronostico1,pronostico2), pronosticosDeJose)
        assertNotEquals(todosLosPronosticos,pronosticosDeJose)
    }

}