package ar.edu.unq.grupo7.pronosticosdeportivos.model.email

class Email {
    //Parameters
    val user = "pdeportivos.ok@gmail.com"
    val pass = "pekatblrilyjsfhs"
    var subject = ""
    var receiver = ""
    var message = ""
    var filePath = ""
    var fileName = ""

    fun composeEmailWith(subject: String, receiver: String, message: String) {
        this.subject = subject
        this.receiver = receiver
        this.message = message
    }
}