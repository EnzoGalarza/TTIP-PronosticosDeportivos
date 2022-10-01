package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
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

    @Mock private lateinit var pronostic1 : Pronostic
    @Mock private lateinit var pronostic2 : Pronostic


    @Test
    fun registerPronostics(){
        val pronosticsToSave = mutableListOf(pronostic1,pronostic2)

        Mockito.`when`(pronosticService.saveAll(pronosticsToSave)).thenReturn(mutableListOf(pronostic1,pronostic2))

        val obtainedPronostics = pronosticController.registerPronostics(pronosticsToSave)

        assertEquals(obtainedPronostics.body,pronosticsToSave)
        assertEquals(obtainedPronostics.statusCode,HttpStatus.CREATED)
    }

    @Test
    fun getAllPronosticsFromUser(){
        Mockito.`when`(pronosticService.pronosticsFromUser("jose")).thenReturn(listOf(pronostic1))

        val todosLosPronosticos = listOf(pronostic1,pronostic2)
        val pronosticosDeJose = pronosticController.getPronosticsFromUser("jose")

        assertEquals(1,pronosticosDeJose.size)
        assertEquals(listOf(pronostic1), pronosticosDeJose)
        assertNotEquals(todosLosPronosticos,pronosticosDeJose)
    }

}