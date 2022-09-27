package ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.MatchDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.ResultDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.ScoreDTO
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "partidos")
class Match(@OneToMany(cascade = [CascadeType.ALL])
            var teams : List<Team>, @Column var date : LocalDateTime, @Column var localGoals : Int?,
            @Column var awayGoals : Int?, @Column val code : Long, @Column var status : String,
            @Column var matchDay : Int, @Column var competition : String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0

}

fun Match.toDTO() = MatchDTO(id = code, status = status, utcDate = date, matchday = matchDay,homeTeam = teams[0].toDTO(),
awayTeam = teams[1].toDTO(), score = ScoreDTO("",ResultDTO(localGoals,awayGoals))
)

