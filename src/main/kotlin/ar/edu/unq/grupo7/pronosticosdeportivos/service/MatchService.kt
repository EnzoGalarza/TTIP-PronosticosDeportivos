package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.MatchDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.MatchListDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.toDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.MatchNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.NoMatchesException
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.MatchRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.NullPointerException

@Service
class MatchService {

    val entity = GenerateHeader.generateHeader()

    @Autowired
    lateinit var restTemplate : RestTemplate

    @Autowired
    lateinit var repository : MatchRepository

    @Autowired
    lateinit var teamService: TeamService

    fun getMatches(competition: String, matchDay: Int): List<MatchDTO> {
        var matchesList: List<Match> = repository.findByCompetitionAndMatchDay(matchDay,competition);
        val notFinished: List<Match> = matchesList.filter { it.status != "FINISHED" }
        if(matchesList.isEmpty() || notFinished.isNotEmpty()){
            val response = restTemplate.exchange("https://api.football-data.org/v4/competitions/${competition}/matches?matchday=${matchDay}", HttpMethod.GET,entity,
                object : ParameterizedTypeReference<MatchListDTO>() {}
            )
            if  (matchesList.size < response.body!!.matches.size){
                try {
                    matchesList = response.body!!.matches.map {
                        val localTeam = teamService.saveTeam(it.homeTeam.toModel())
                        val awayTeam = teamService.saveTeam(it.awayTeam.toModel())

                        it.toModel(competition, localTeam, awayTeam)
                    }
                } catch (e : NullPointerException){
                    throw NoMatchesException("La liga no tiene partidos disponibles por el momento")
                }
            }
            else{
                for (match : Match in matchesList){
                    val matchDTO : MatchDTO? = response.body!!.matches.find { it.id == match.code }
                    if (matchDTO != null){
                        match.status = matchDTO.status
                        match.date = matchDTO.utcDate
                        match.localGoals = matchDTO.score.fullTime.home
                        match.awayGoals = matchDTO.score.fullTime.away
                    }
                }
            }
            repository.saveAll(matchesList)
            return response.body!!.matches
        }
        return matchesList.map { it.toDTO() }
    }

    fun getByCode(code : Long) : Match {
        return repository.findByCode(code).orElseThrow { MatchNotFoundException("No se encontró el partido que quiere pronosticar") }
    }

}
