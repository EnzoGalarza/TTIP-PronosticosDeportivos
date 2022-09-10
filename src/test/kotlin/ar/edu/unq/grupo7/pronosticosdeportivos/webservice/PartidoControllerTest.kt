package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.EquipoDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.PartidoDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.ResultDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.ScoreDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PartidoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class PartidoControllerTest(@Mock val partidoService : PartidoService) {

    @InjectMocks
    lateinit var partidoController : PartidoController

    @Test
    fun obtenerPartidosDeCompetencia(){
        val eq1 = EquipoDTO("Manchester United","MUN","logoMun")
        val eq2 = EquipoDTO("Southampton FC","SOU","logoSou")
        val eq3 = EquipoDTO("Aston Villa FC","SOU","logoSou")
        val score = ScoreDTO("DRAW", ResultDTO(1,2))
        val partido1 = PartidoDTO("FINISHED","2022-11-05T15:00:00Z",25,eq1,eq2,score)
        val partido2 = PartidoDTO("FINISHED","2022-11-05T15:00:00Z",30,eq1,eq3,score)

        Mockito.`when`(partidoService.getPartidos("PL")).thenReturn(mutableListOf(partido1,partido2))

        var todosLosPartidos = partidoController.getTodosLosPartidos("PL")

        assertEquals(mutableListOf(partido1,partido2),todosLosPartidos.body)
        assertEquals(HttpStatus.OK,todosLosPartidos.statusCode)
    }
}