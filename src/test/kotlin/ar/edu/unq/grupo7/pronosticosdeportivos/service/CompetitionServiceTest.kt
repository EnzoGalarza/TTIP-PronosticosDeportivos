package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.CompetitionBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CompetitionDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CompetitionsDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CurrentSeasonDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.CompetitionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class CompetitionServiceTest(@Mock val competitionRepository: CompetitionRepository, @Mock val competitionTemplate : RestTemplate) {

    @InjectMocks
    lateinit var competitionService: CompetitionService

    private val date: LocalDate = LocalDate.now().plusDays(2)
    private val competitionDTO = CompetitionDTO("Comp 1", "Emb 1", "C1", CurrentSeasonDTO(1,date))
    private val competitionDTO2 = CompetitionDTO("Comp 2", "Emb 2", "PD", CurrentSeasonDTO(3,date))
    @Mock lateinit var competitionsDTO: CompetitionsDTO
    @Mock lateinit var currentSeasonDTO: CurrentSeasonDTO

    lateinit var entity : HttpEntity<Any>
    lateinit var headers : HttpHeaders

    val competitionBuilder = CompetitionBuilder()

    @BeforeEach
    fun setUp(){
        headers = HttpHeaders()
        headers.set("X-Auth-Token","f5bedce0ca024352a218d300e71d0798")
        entity = HttpEntity("parameters",headers)


    }

    @Test
    fun getCompetitionsWithSavedCompetitions(){
        val competition1 = competitionBuilder.build()
        val competition2 = competitionBuilder.build()

        Mockito.`when`(competitionRepository.count()).thenReturn(2)
        Mockito.`when`(competitionRepository.findAll()).thenReturn(mutableListOf(competition1,competition2))

        val competitionList = competitionService.getCompetitions()

        assertEquals(competitionList, mutableListOf(competition1,competition2))
    }

    @Test
    fun getCompetitionsWithoutSavedCompetitions(){
        Mockito.`when`(competitionRepository.count()).thenReturn(0L)
        Mockito.`when`(competitionsDTO.competitions).thenReturn(mutableListOf(competitionDTO,competitionDTO2))
        Mockito.`when`(competitionTemplate.exchange("https://api.football-data.org/v4/competitions",HttpMethod.GET,entity,
            object : ParameterizedTypeReference<CompetitionsDTO>() {}))
            .thenReturn(ResponseEntity(competitionsDTO,HttpStatus.OK))

        val competitionList = competitionService.getCompetitions()

        assertEquals(competitionList.size,2)
        Mockito.verify(competitionRepository,times(1)).saveAll(competitionList)
    }

    @Test
    fun getCurrentMatchDay(){
        Mockito.`when`(competitionRepository.findByCode("PD")).thenReturn(competitionDTO2.toModel())
        Mockito.`when`(competitionTemplate.exchange("https://api.football-data.org/v4/competitions/PD/",HttpMethod.GET,entity,
            object : ParameterizedTypeReference<CurrentSeasonDTO>(){}
        )).thenReturn(ResponseEntity(currentSeasonDTO,HttpStatus.OK))

        val day = competitionService.getCurrentMatchDay("PD")

        assertEquals(day,3)
    }
}