package ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.UserTournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.DuplicateUserInTournament
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.IllegalCriteriaException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.NoCriteriaTournamentError
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.TournamentNameLengthException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.*
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import java.time.LocalDate
import javax.persistence.*

@NamedNativeQuery(name = "Tournament.findByUserId",
                  query = "SELECT tournament.id AS id, tournament.name AS name, tournament.competition as competition\n" +
                          "FROM tournament\n" +
                          "INNER JOIN tournament_user on tournament.id = tournament_user.tournament_id\n" +
                          "INNER JOIN user_score on tournament_user.user_score_id = user_score.id\n" +
                          "WHERE user_score.user_score_id = ?1",
                  resultSetMapping = "Mapping.UserTournamentDTO")
@SqlResultSetMapping(name = "Mapping.UserTournamentDTO",
                    classes = (arrayOf(ConstructorResult(targetClass = UserTournamentDTO::class,
                    columns = (arrayOf(ColumnResult(name = "id", type = Long::class),
                                       ColumnResult(name = "name", type = String::class),
                                       ColumnResult(name = "competition", type = String::class)))))))
@Entity
class Tournament(@Column val name : String,
                 @Column val competition : String,
                 @OneToMany(cascade = [CascadeType.ALL])
                 @JoinColumn(name = "tournamentId")
                 val criterias : List<Criteria>,
                 @Column val endDate: LocalDate) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToMany(fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    @JoinTable(name="Tournament_User",
        joinColumns=[JoinColumn(name="tournamentId")],
        inverseJoinColumns=[JoinColumn(name="userScoreId")])
    var users : MutableList<UserScore> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "tournamentId")
    var evaluatedPronostic : MutableList<EvaluatedPronostic> = mutableListOf()

    fun addUser(user: User){
        validate(listOf(user.username))
        this.users.add(UserScore(user,0))
    }

    fun getCriteria(criteria : String) : PronosticCriteria{
        var pronosticCriteria : PronosticCriteria? = null
        when(criteria){
            "Complete" -> pronosticCriteria = CompleteResult
            "Partial" -> pronosticCriteria = PartialResult
            "Approach" -> pronosticCriteria = Approach
            "WinnerOrTie" -> pronosticCriteria = WinnerOrTie
            else -> {
                throw IllegalCriteriaException("Criterio invalido")
            }
        }
        return pronosticCriteria!!
    }

    fun addEvaluatedPronostic(id: Long) {
        this.evaluatedPronostic.add(EvaluatedPronostic(id))
    }

    fun validate(userEmails : List<String> = listOf()){
        require(name.length >= 3){
            throw TournamentNameLengthException("El tamaño mínimo del nombre son 3 caracteres")
        }
        require(name.length <= 20){
            throw TournamentNameLengthException("El tamaño máximo del nombre son 20 caracteres")
        }

        require(criterias.isNotEmpty()){
            throw NoCriteriaTournamentError("Se debe tener al menos un criterio de puntuación")
        }

        if(userEmails.isNotEmpty()) {
            users.forEach {
                require(!userEmails.contains(it.user.username)) {
                    throw DuplicateUserInTournament("El usuario ${it.user.username} ya está en el torneo")
                }
            }
        }
    }

}