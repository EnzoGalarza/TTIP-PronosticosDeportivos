package ar.edu.unq.grupo7.pronosticosdeportivos.model.email

class Sender(msg: String?, var email: Email) : Thread(msg) {
    var controller: Controller = Controller()

    override fun run() {
        controller.sendMail(email)
    }
}