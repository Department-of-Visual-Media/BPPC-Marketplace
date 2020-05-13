package a.suman.bppcmarketplace.Login.Model

import a.suman.bppcmarketplace.BPPCDatabase
import a.suman.bppcmarketplace.BasicUserData
import a.suman.bppcmarketplace.Login.Model.Network.RetrofitClient
import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.TokenClass
import android.app.Application
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class LoginRepository(val application: Application) {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val apiInstance=RetrofitClient.instance!!.api

    private val authenticationService =BPPCDatabase.getBPPCDatabase(application).getAuthenticationServices()



    fun getGoogleSignInIntent(): Intent {

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.OAuth2_client_id))
                .requestEmail().requestProfile().requestScopes(
                    Scope(Scopes.EMAIL), Scope(Scopes.PROFILE))
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)

        return mGoogleSignInClient.signInIntent
    }


    fun googleSignInComplete(data: Intent?): Completable {
        try {
            val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                Log.d("REPO", "${account.displayName}")
                val googleSignInToken = account.idToken.toString()
                val name= account.displayName
                val email=account.email
                 return apiInstance.authWithBackend(googleSignInToken).subscribeOn(Schedulers.io()).flatMapCompletable {
//                    val auth=FirebaseAuth.getInstance()
//                   auth.signInAnonymously()
                     authenticationService.insertBasicUserData(it).doOnComplete{TokenClass.token=it.token}
                 }
            }else{
                throw Exception()
            }
        } catch (e: Exception) {
            Log.d("REPO", "$e")
            return Completable.error(e)
        }
    }

    fun observeForToken(): Single<List<BasicUserData?>> {
        return authenticationService.getBasicUserData().subscribeOn(Schedulers.io())
    }





}