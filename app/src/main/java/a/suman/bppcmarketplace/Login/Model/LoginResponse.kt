package a.suman.bppcmarketplace.Login.Model

class LoginResponse(
    token: String,

    isNew: Boolean
) {

    var token: String

    var isNew: Boolean


    init {

        this.token = token
        this.isNew = isNew
    }
}