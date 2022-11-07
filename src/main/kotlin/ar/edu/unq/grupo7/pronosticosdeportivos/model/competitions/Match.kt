package ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.MatchDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.ResultDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.ScoreDTO
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "match")
class Match(@OneToOne
            @Cascade(CascadeType.SAVE_UPDATE, CascadeType.DETACH, CascadeType.MERGE)
            var homeTeam : Team,
            @OneToOne
            @Cascade(CascadeType.SAVE_UPDATE, CascadeType.DETACH, CascadeType.MERGE)
            var awayTeam : Team, @Column var date : LocalDateTime?, @Column var localGoals : Int?,
            @Column var awayGoals : Int?, @Column val code : Long, @Column var status : String,
            @Column var matchDay : Int, @Column var competition : String) : Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0

}

fun Match.toDTO() = MatchDTO(id = code, status = status, utcDate = date!!, matchday = matchDay,homeTeam = homeTeam.toDTO(),
awayTeam = awayTeam.toDTO(), score = ScoreDTO("",ResultDTO(localGoals,awayGoals)))

/*fun Match.update(newMatch: Match) = Match(homeTeam = newMatch.homeTeam, awayTeam = newMatch.awayTeam,
                                          date = newMatch.date, status = newMatch.status, matchDay = newMatch.matchDay,
                                          competition = newMatch.competition)*/

