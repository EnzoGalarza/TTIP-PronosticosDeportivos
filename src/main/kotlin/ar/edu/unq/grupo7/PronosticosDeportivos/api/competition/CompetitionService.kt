package ar.edu.unq.grupo7.PronosticosDeportivos.api.competition

import ar.edu.unq.grupo7.PronosticosDeportivos.model.competition.Competition
import org.springframework.stereotype.Service

@Service
class CompetitionService {

    fun getCompetitions() : MutableList<Competition>?{

        val competitions: MutableList<Competition>? = mutableListOf()

        return competitions

    }

}