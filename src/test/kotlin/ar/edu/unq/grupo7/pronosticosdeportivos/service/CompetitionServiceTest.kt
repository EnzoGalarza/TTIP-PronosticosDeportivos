package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CompetitionsDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CurrentSeasonDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.SeasonDTO
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
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
class CompetitionServiceTest(@Mock val competitionRepository: CompetitionRepository, @Mock val competitionTemplate : RestTemplate) {

    @InjectMocks lateinit var competitionService: CompetitionService

    @Mock lateinit var competition : Competition
    @Mock lateinit var competition2 : Competition
    @Mock lateinit var competitionsDTO: CompetitionsDTO
    @Mock lateinit var seasonDTO : SeasonDTO
    @Mock lateinit var currentSeasonDTO: CurrentSeasonDTO
    lateinit var entity : HttpEntity<Any>
    lateinit var headers : HttpHeaders

    @BeforeEach
    fun setUp(){
        headers = HttpHeaders()
        headers.set("X-Auth-Token","f5bedce0ca024352a218d300e71d0798")
        entity = HttpEntity("parameters",headers)
    }

    @Test
    fun getCompetitionsWithSavedCompetitions(){
        Mockito.`when`(competitionRepository.count()).thenReturn(2)
        Mockito.`when`(competitionRepository.findAll()).thenReturn(mutableListOf(competition,competition2))

        val competitionList = competitionService.getCompetitions()

        assertEquals(competitionList, mutableListOf(competition,competition2))
    }

    @Test
    fun getCompetitionsWithoutSavedCompetitions(){
        Mockito.`when`(competitionRepository.count()).thenReturn(0L)
        Mockito.`when`(competitionsDTO.competitions).thenReturn(mutableListOf(competition,competition2))
        Mockito.`when`(competitionTemplate.exchange("https://api.football-data.org/v4/competitions",HttpMethod.GET,entity,
            object : ParameterizedTypeReference<CompetitionsDTO>() {}))
            .thenReturn(ResponseEntity(competitionsDTO,HttpStatus.OK))

        val competitionList = competitionService.getCompetitions()

        assertEquals(competitionList, mutableListOf(competition,competition2))
        Mockito.verify(competitionRepository,times(1)).saveAll(competitionList)
    }

    @Test
    fun getCurrentMatchDay(){
        Mockito.`when`(currentSeasonDTO.currentMatchday).thenReturn(3)
        Mockito.`when`(seasonDTO.currentSeason).thenReturn(currentSeasonDTO)
        Mockito.`when`(competitionTemplate.exchange("https://api.football-data.org/v4/competitions/PD/",HttpMethod.GET,entity,
            object : ParameterizedTypeReference<SeasonDTO>(){}
        )).thenReturn(ResponseEntity(seasonDTO,HttpStatus.OK))

        val day = competitionService.getCurrentMatchDay("PD")

        assertEquals(day,3)
    }
}