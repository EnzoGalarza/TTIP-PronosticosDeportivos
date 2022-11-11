package ar.edu.unq.grupo7.pronosticosdeportivos.model

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.TournamentBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.DuplicateUserInTournament
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.IllegalCriteriaException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.NoCriteriaTournamentError
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.TournamentNameLengthException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Approach
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.CompleteResult
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.PartialResult
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.WinnerOrTie
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class TournamentTest {

    private val tournamentBuilder = TournamentBuilder()

    @Mock
    lateinit var user : User

    @Test
    fun aTournamentHasNameCompetitionCriteriaUsersScoresAndEvaluatedPronostics(){
        val criteria = listOf(Criteria("Complete",3), Criteria("WinnerOrTie",1))
        val newTournament = tournamentBuilder.withName("Tournament").withCompetition("PL").withCriterias(criteria).build()

        assertEquals(newTournament.name,"Tournament")
        assertEquals(newTournament.competition,"PL")
        assertEquals(newTournament.criterias.size,2)
        assertEquals(newTournament.users.size,0)
        assertEquals(newTournament.evaluatedPronostic.size,0)
    }

    @Test
    fun addUserToTournament(){
        val newTournament = tournamentBuilder.build()

        newTournament.addUser(user)

        assertEquals(newTournament.users.size,1)
    }

    @Test
    fun cantAddTwoTimesTheSameUserToTournament(){
        val newTournament = tournamentBuilder.build()

        newTournament.addUser(user)

        assertThrows<DuplicateUserInTournament> {
            newTournament.addUser(user)
        }
    }

    @Test
    fun validateTournamentWithIllegalNameLength(){
        val newTournament = tournamentBuilder.withName("T1").build()

        assertThrows<TournamentNameLengthException> {
            newTournament.validate()
        }
    }

    @Test
    fun validateTournamentWithNoCriteria(){
        val newTournament = tournamentBuilder.withCriterias(listOf()).build()

        assertThrows<NoCriteriaTournamentError> {
            newTournament.validate()
        }
    }

    @Test
    fun addEvaluatedPronosticFromAnId(){
        val newTournament = tournamentBuilder.build()

        newTournament.addEvaluatedPronostic(2)

        assertEquals(newTournament.evaluatedPronostic.size,1)
        assertEquals(newTournament.evaluatedPronostic[0].pronosticId,2)
    }

    @Test
    fun getCriteria(){
        val newTournament = tournamentBuilder.build()

        assertEquals(newTournament.getCriteria("Complete"),CompleteResult)
        assertEquals(newTournament.getCriteria("Partial"),PartialResult)
        assertEquals(newTournament.getCriteria("Approach"),Approach)
        assertEquals(newTournament.getCriteria("WinnerOrTie"),WinnerOrTie)

        assertThrows<IllegalCriteriaException> {
            newTournament.getCriteria("Otracosa")
        }
    }
}