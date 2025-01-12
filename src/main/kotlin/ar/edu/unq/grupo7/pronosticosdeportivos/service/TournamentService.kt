package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentResultsDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.UserTournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.Email
import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.MessageBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.email.Sender
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.NotificationNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.TournamentNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UserNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.UserScore
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.Notification
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class TournamentService {

    @Autowired
    private lateinit var tournamentRepository : TournamentRepository

    @Autowired
    private lateinit var userRepository : UserRepository

    @Autowired
    private lateinit var userService : UserService

    @Autowired
    private lateinit var userScoreRepository : UserScoreRepository

    @Autowired
    private lateinit var pronosticService: PronosticService

    @Autowired
    private lateinit var competitionRepository: CompetitionRepository

    @Autowired
    private lateinit var notificationRepository: NotificationRepository

    private val messageBuilder: MessageBuilder = MessageBuilder()

    @Transactional
    fun saveTournament(tournament: TournamentDTO) : Tournament{
        try {
            val createdTournament = tournament.toModel(competitionRepository.findByCode(tournament.competition).endDate)
            createdTournament.validate()
            for(userEmail in tournament.usersEmail){
                val getUser = userRepository.findByEmail(userEmail).get()
                createdTournament.addUser(getUser)
            }
            return tournamentRepository.save(createdTournament)
        } catch (e:NoSuchElementException){
            throw UserNotFoundException("No se encontró algún usuario")
        }
    }

    @Transactional
    fun getTournamentsFromUser(userId : Long) : List<UserTournamentDTO>{
        return tournamentRepository.findByUserId(userId)
    }

    @Transactional
    fun updateTournament(tournamentId : Long){
        val tournament = tournamentRepository.findById(tournamentId).get()
        for(user in tournament.users){
            val pronosticsFromUser = pronosticService.notEvaluatedPronostics(user.user.username,tournament.competition,tournamentId)
            for(pronostic in pronosticsFromUser){
                tournament.addEvaluatedPronostic(pronostic.id)
                for(criteria in tournament.criterias){
                    val tournamentCriteria = tournament.getCriteria(criteria.name)
                    if(tournamentCriteria.eval(pronostic, pronostic.match)) {
                        user.sumPoints(criteria.score)
                        user.addHit()
                    }
                }
                user.addPronostic()
            }
            user.calculatePercentage(tournament.criterias.size)
        }
        tournamentRepository.save(tournament)
    }

    @Transactional
    fun inviteUsers(tournamentId: Long, users: List<String>) {
        val tournament = tournamentRepository.findById(tournamentId).orElseThrow {
            throw TournamentNotFoundException("No se encontró el torneo")
        }

        tournament.validate(users)

        for(userEmail in users){
            val user = userRepository.findByEmail(userEmail).orElseThrow {
                throw UserNotFoundException("Usuario ${userEmail} no se encuentra registrado")
            }
            user.addNotification(Notification(true,"Invitación al torneo ${tournament.name}", tournamentId))
            userRepository.save(user)
        }

        val competition = competitionRepository.findByCode(tournament.competition)

        val sender = Sender("Client send", this.buildInviteEmail(users,tournament.name, competition.name))
        sender.start()
    }

    private fun buildInviteEmail(usersEmail : List<String>, name : String, competition : String): Email {
        val email = Email()

        email.composeEmailWith("Te invitaron al torneo $name", usersEmail, messageBuilder.inviteUsers(name, competition))
        return email
    }

    @Transactional
    fun getTournamentsUserScores(tournamentId : Long): TournamentResultsDTO {
        val tournament = tournamentRepository.findById(tournamentId).get()
        return TournamentResultsDTO(userScoreRepository.findTournamentScores(tournamentId),
                                    tournament.endDate.isBefore(LocalDate.now()))
    }

    @Transactional
    fun addUserToTournament(tournamentId: Long, userEmail: String, invitationId : Long) {
        val tournament = tournamentRepository.findById(tournamentId).orElseThrow {
            throw TournamentNotFoundException("No se encontró el torneo")
        }

        val user = userRepository.findByEmail(userEmail).orElseThrow {
            throw UserNotFoundException("Usuario ${userEmail} no se encuentra registrado")
        }

        val notification = notificationRepository.findById(invitationId).orElseThrow {
            throw NotificationNotFoundException("No existe la notificacion que se intenta aceptar")
        }

        tournament.addUser(user)
        notificationRepository.delete(notification)
        userService.deleteNotification(user.id, notification)
        tournamentRepository.save(tournament)
    }

}
