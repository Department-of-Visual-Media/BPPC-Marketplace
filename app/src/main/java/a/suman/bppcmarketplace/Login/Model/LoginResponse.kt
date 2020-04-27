package a.suman.bppcmarketplace.Login.Model

data class LoginResponse(
    var token: String,
    var username: String,
    var email: String,
    var isNew: Boolean
)