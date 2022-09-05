package ar.edu.unq.grupo7.PronosticosDeportivos.model.competition

class Competition {
    var id: Long = 0
    var name: String? = null

    //Constructor
    constructor() {}
    constructor(id: Long, name: String?){
        this.id = id
        this.name = name
    }

}