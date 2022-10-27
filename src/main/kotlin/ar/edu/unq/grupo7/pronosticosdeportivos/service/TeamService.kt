package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService {

    @Autowired
    lateinit var repository: TeamRepository

    @Transactional
    fun saveTeam(team : Team) : Team{
        val savedTeam = repository.findByTla(team.tla)
        if(savedTeam.isPresent){
            return repository.save(savedTeam.get())
        }
        return repository.save(team)
    }
}