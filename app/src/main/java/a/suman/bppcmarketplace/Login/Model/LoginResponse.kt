package a.suman.bppcmarketplace.Login.Model

class LoginResponse(
    token: String,
    username: String,
    email: String
) {

    private var token: String
    private var username: String
    private var email: String


    init {
        this.email = email
        this.username = username
        this.token = token
    }
}