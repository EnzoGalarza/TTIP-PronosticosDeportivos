package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.MatchDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.MatchListDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.toDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.MatchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MatchService {

    @Autowired
    lateinit var restTemplate : RestTemplate

    @Autowired
    lateinit var repository : MatchRepository

    fun getMatches(competition: String, matchDay: Int): List<MatchDTO> {
        if(repository.countMatchesByCompetitionAndDay(matchDay,competition) == 0){
            var headers : HttpHeaders = HttpHeaders()
            headers.set("X-Auth-Token","f5bedce0ca024352a218d300e71d0798")
            var entity = HttpEntity("parameters",headers)

            var response = restTemplate.exchange("https://api.football-data.org/v4/competitions/${competition}/matches?matchday=${matchDay}", HttpMethod.GET,entity,
                object : ParameterizedTypeReference<MatchListDTO>() {}
            )

            repository.saveAll(response.body!!.matches.map { it.toModel(competition) })
            return response.body!!.matches
        }

        return repository.findByMatchDay(matchDay).map { it.toDTO() }
    }

    fun getByCode(code : Long) : Match {
        return repository.findByCode(code)
    }

}
