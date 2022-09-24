package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CompetitionsDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.Competition
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CurrentSeasonDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.SeasonDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.CompetitionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CompetitionService {

    @Autowired
    lateinit var restTemplate : RestTemplate

    @Autowired
    lateinit var repository : CompetitionRepository

    fun getCompetitions() : MutableList<Competition>{
        if(repository.count() == 0L){
            var headers : HttpHeaders = HttpHeaders()
            headers.set("X-Auth-Token","f5bedce0ca024352a218d300e71d0798")
            var entity = HttpEntity("parameters",headers)

            var response = restTemplate.exchange("https://api.football-data.org/v4/competitions",HttpMethod.GET,entity,
                object : ParameterizedTypeReference<CompetitionsDTO>() {}
            )
            val competitions = response.body!!.competitions

            repository.saveAll(competitions)
            return competitions
        }

        return repository.findAll()
    }

    fun getCurrentMatchDay(competition: String) : Int{
        var headers : HttpHeaders = HttpHeaders()
        headers.set("X-Auth-Token","f5bedce0ca024352a218d300e71d0798")

        var entity = HttpEntity("parameters",headers)

        var response = restTemplate.exchange("https://api.football-data.org/v4/competitions/${competition}/",HttpMethod.GET,entity,
            object : ParameterizedTypeReference<SeasonDTO>(){}
        )

        return response.body!!.currentSeason.currentMatchday
    }

}