package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.PronosticRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PronosticService {

    @Autowired
    private lateinit var repository : PronosticRepository

    @Autowired
    private lateinit var matchService : MatchService

    fun pronosticsFromUser(user:String) : List<Pronostic>{
        return repository.findByUser(user)
    }

    @Transactional
    fun saveAll(pronosticList: List<Pronostic>) : List<Pronostic>{
        var newPronostics : MutableList<Pronostic> = mutableListOf()
        for(pronostic in pronosticList){
            var savedPronostic = repository.findById(pronostic.id)
            if(savedPronostic.isPresent){
                savedPronostic.get().updateGoals(pronostic)
            } else{
                var savedMatch = matchService.getByCode(pronostic.match.code)
                pronostic.updateMatch(savedMatch)
            }
            newPronostics.add(repository.save(pronostic))
        }
        return newPronostics
    }

    @Transactional
    fun updateGoals(pronosticsToUpdate : MutableList<Pronostic>){
        for(pronostic in pronosticsToUpdate){
            repository.updatePronostic(pronostic.id,pronostic.localGoals,pronostic.awayGoals)
        }
    }
}
