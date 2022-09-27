package ar.edu.unq.grupo7.pronosticosdeportivos.model.email

class Sender(email: Email) {
    var email: Email
    var controller: Controller

    init {
        this.email = email
        controller = Controller()
    }

    fun send() {
        controller.sendMail(email)
    }
}