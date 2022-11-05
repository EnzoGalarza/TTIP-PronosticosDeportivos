package ar.edu.unq.grupo7.pronosticosdeportivos.model.email

class Email {
    //Parameters
    val user = "pdeportivos.ok@gmail.com"
    val pass = "pekatblrilyjsfhs"
    var subject = ""
    var recipients = listOf<String>()
    var message = ""
    var filePath = ""
    var fileName = ""

    fun composeEmailWith(subject: String, recipients: List<String>, message: String) {
        this.subject = subject
        this.recipients = recipients
        this.message = message
    }
}