package a.suman.bppcmarketplace.Login.ViewModel

import a.suman.bppcmarketplace.Login.Model.LoginRepository
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import javax.security.auth.login.LoginException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val RC_SIGN_IN = 1
    private var repo: LoginRepository =
        LoginRepository(application)
    val TokenLiveData= liveData{emitSource(repo.backendTokenLiveData) }
    val LoginStatusLiveData= liveData {emitSource(repo.loginStatusLiveData)}


    fun initGoogleSignIn(): Intent {
        return repo.getGoogleSignInIntent()
    }

    fun onResultFromActivity(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_SIGN_IN -> {
                repo.googleSignInComplete(data)
            }
            //
        }
    }


    fun clearDisposables() {
        repo.clearDisposables()
    }


    companion object {
        const val TAG: String = "LoginViewModel"
    }

}