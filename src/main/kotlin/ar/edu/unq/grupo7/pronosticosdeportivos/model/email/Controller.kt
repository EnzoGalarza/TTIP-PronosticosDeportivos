package ar.edu.unq.grupo7.pronosticosdeportivos.model.email

import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.EmailSendErrorException
import java.util.*
import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


class Controller {
    fun sendMail(mail: Email) {
        try {
            val p = Properties()
            p["mail.smtp.host"] = "smtp.gmail.com"
            p.setProperty("mail.smtp.starttls.enable", "true")
            p.setProperty("mail.smtp.port", "587")
            p.setProperty("mail.smtp.user", mail.user)
            p.setProperty("mail.smtp.auth", "true")
            val s: Session = Session.getDefaultInstance(p, null)
            val text: BodyPart = MimeBodyPart()
            text.setText(mail.message)
            val attachment: BodyPart = MimeBodyPart()
            val multi = MimeMultipart()
            multi.addBodyPart(text)
            if (!mail.filePath.equals("")) {
                attachment.setDataHandler(javax.activation.DataHandler(javax.activation.FileDataSource(mail.filePath)))
                attachment.setFileName(mail.fileName)
                multi.addBodyPart(attachment)
            }
            val message = MimeMessage(s)
            message.setFrom(InternetAddress(mail.user))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(mail.receiver))
            message.setSubject(mail.subject)
            message.setText(mail.message, "utf-8", "html")
            val t: Transport = s.getTransport("smtp")
            t.connect(mail.user, mail.pass)
            t.sendMessage(message, message.getAllRecipients())
            t.close()
        } catch (e: Exception) {
            throw EmailSendErrorException("Error en el envio de email")
        }
    }
}