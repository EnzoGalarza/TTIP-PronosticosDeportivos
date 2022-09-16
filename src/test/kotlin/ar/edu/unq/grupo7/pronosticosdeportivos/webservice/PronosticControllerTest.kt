package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PronosticService
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
class PronosticControllerTest(@Mock val pronosticService : PronosticService) {

    @InjectMocks
    lateinit var pronosticController: PronosticController

    @Test
    fun registerPronostics(){
        val pronostic1 = Pronostic("pedro",33,2,2)
        val pronostic2 = Pronostic("pedro",34,2,1)

        val pronosticsToSave = mutableListOf(pronostic1,pronostic2)

        Mockito.`when`(pronosticService.saveAll(pronosticsToSave)).thenReturn(mutableListOf(pronostic1,pronostic2))

        val obtainedPronostics = pronosticController.registerPronostics(pronosticsToSave)

        assertEquals(obtainedPronostics.body,pronosticsToSave)
        assertEquals(obtainedPronostics.statusCode,HttpStatus.CREATED)
    }

    @Test
    fun getAllPronosticsFromUser(){
        val pronostic1 = Pronostic("jose",34,2,1)
        val pronostic2 = Pronostic("jose",35,2,2)
        val pronostic3 = Pronostic("pedro",34,2,1)

        Mockito.`when`(pronosticService.pronosticsFromUser("jose")).thenReturn(listOf(pronostic1,pronostic2))

        val todosLosPronosticos = listOf(pronostic1,pronostic2,pronostic3)
        val pronosticosDeJose = pronosticController.getPronosticsFromUser("jose")

        assertEquals(2,pronosticosDeJose.size)
        assertEquals(listOf(pronostic1,pronostic2), pronosticosDeJose)
        assertNotEquals(todosLosPronosticos,pronosticosDeJose)
    }

}