package a.suman.bppcmarketplace.Login.Model

class LoginResponse(
    token: String,
    username: String,
    email: String,
    isNew: Boolean
) {

    var token: String
    var username: String
    var email: String
    var isNew: Boolean


    init {
        this.email = email
        this.username = username
        this.token = token
        this.isNew = isNew
    }
}