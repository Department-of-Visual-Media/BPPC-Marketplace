package a.suman.bppcmarketplace.Login.Model

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("/auth/register/")
    fun registerWithBackend(@Field("id_token") token: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("/auth/login/")
    fun loginWithBackend(@Field("id_token") token: String): Observable<LoginResponse>
}