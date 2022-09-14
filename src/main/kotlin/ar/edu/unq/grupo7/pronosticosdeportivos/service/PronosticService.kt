package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.PronosticRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PronosticService {

    @Autowired
    private lateinit var repository : PronosticRepository

    fun pronosticsFromUser(user:String) : List<Pronostic>{
        return repository.pronosticsFromUser(user)
    }

    fun save(pronostic: Pronostic): Pronostic{
        return repository.save(pronostic)
    }
}
