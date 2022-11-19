package ar.edu.unq.grupo7.pronosticosdeportivos.builders

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User

class UserBuilder(
    var name : String = "user name",
    var email : String = "user@hotmail.com",
    var password : String = "123",
    var profileImg : String = "profile.png"
) {
    fun build() : User {
        val user = User()
        user.setName(this.name)
        user.setEmail(this.email)
        user.setProfileImage(this.profileImg)
        user.password = this.password
        return user
    }

    fun withEmail(email : String) : UserBuilder{
        this.email = email
        return this
    }

    fun withName(name: String): UserBuilder {
        this.name = name
        return this
    }
}