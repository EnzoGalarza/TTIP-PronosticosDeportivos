package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CompetitionsDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.CompetitionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CompetitionService {

    private val entity = GenerateHeader.generateHeader()

    @Autowired
    lateinit var restTemplate : RestTemplate

    @Autowired
    lateinit var repository : CompetitionRepository

    fun getCompetitions() : List<Competition>{
        if(repository.count() == 0L){
            val response = restTemplate.exchange("https://api.football-data.org/v4/competitions",HttpMethod.GET,entity,
                object : ParameterizedTypeReference<CompetitionsDTO>() {}
            )
            val competitions = response.body!!.competitions.map { it.toModel() }

            repository.saveAll(competitions)
            return competitions
        }

        return repository.findAll()
    }

    fun getCurrentMatchDay(competition: String) : Int{
        return repository.findByCode(competition).currentMatchday
    }

}