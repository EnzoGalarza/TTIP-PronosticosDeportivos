package ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions

class EmailAlreadyConfirmedException(override val message: String?) : Exception()

class EmailSendErrorException(override val message: String?) : Exception()

class ExpirationDayException(override var message: String?) : Exception()

class InvalidEmailException(override val message: String?) : Exception()

class InvalidPasswordException(override val message: String?) : Exception()

class TokenExpiredException(override val message: String?) : Exception()

class TokenNotFoundException(override val message: String?) : Exception()

class UsedEmailException(override val message: String?) : Exception()

class UserDisabledException(override val message: String?) : Exception()

class UserNotFoundException(override val message: String?) : Exception ()

class TournamentNameLengthException(override val message: String?) : Exception()

class InvalidNameException(override val message: String?) : Exception()

class MatchNotFoundException(override val message: String?) : Exception()

class TournamentNotFoundException(override val message: String?) : Exception()

class DuplicateUserInTournament(override val message: String?) : Exception()

class NoCriteriaTournamentError(override val message: String?) : Exception()