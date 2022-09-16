package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.PronosticRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PronosticService {

    @Autowired
    private lateinit var repository : PronosticRepository

    fun pronosticsFromUser(user:String) : List<Pronostic>{
        return repository.pronosticsFromUser(user)
    }

    @Transactional
    fun saveAll(pronosticList: List<Pronostic>) : List<Pronostic>{
        for(pronostic in pronosticList){
            var savedPronostic = repository.findByMatchId(pronostic.matchId)
            if(!savedPronostic.isPresent){
                repository.save(pronostic)
            }
            else{
                savedPronostic.get().updateGoals(pronostic)
                repository.updatePronostic(pronostic.id,pronostic.localGoals,pronostic.awayGoals)
            }
        }
        return pronosticList
    }

    @Transactional
    fun updateGoals(pronosticsToUpdate : MutableList<Pronostic>){
        for(pronostic in pronosticsToUpdate){
            repository.updatePronostic(pronostic.id,pronostic.localGoals,pronostic.awayGoals)
        }
    }
}
