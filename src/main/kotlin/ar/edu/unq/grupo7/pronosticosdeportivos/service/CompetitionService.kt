package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.CompetitionsDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.Competition
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

    fun getCompetitions() : MutableList<Competition>{
        var headers : HttpHeaders = HttpHeaders()
        headers.set("X-Auth-Token","f5bedce0ca024352a218d300e71d0798")
        var entity = HttpEntity("parameters",headers)

        var response = restTemplate.exchange("https://api.football-data.org/v4/competitions",HttpMethod.GET,entity,
            object : ParameterizedTypeReference<CompetitionsDTO>() {}
        )

        return response.body!!.competitions
    }

}