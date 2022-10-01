package ar.edu.unq.grupo7.pronosticosdeportivos.model.email

class Sender(email: Email) : Thread() {
    var email: Email
    var controller: Controller

    init {
        this.email = email
        controller = Controller()
    }

    override fun run() {
        controller.sendMail(email)
    }
}