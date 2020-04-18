package a.suman.bppcmarketplace.Login.ViewModel

import a.suman.bppcmarketplace.Login.Model.LoginRepository
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

public class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val RC_SIGN_IN = 1

    var repo: LoginRepository =
        LoginRepository(application) // Class name of Repository is LoginRepository

    fun initGoogleSignIn(): Intent {
        return repo.getGoogleSignInIntent()
    }

    fun onResultFromActivity(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "Sending account to repo")
        when (requestCode) {
            RC_SIGN_IN -> {
                repo.googleSignInComplete(data)
            }
            //
        }
    }

    fun getTokenFromShared() {
        repo.getTokenFromShared()
    }

    fun clearDisposables() {
        repo.clearDisposables()
    }


    companion object {
        const val TAG: String = "LoginViewModel"
    }

    fun backendMutableLiveData(): LiveData<String> {
        return repo.getBackendTokenMutableLiveData()

    }

    fun postToken(){
        repo.postTokenFromShared()
    }



    fun getLoginExceptionLiveData(): LiveData<String> {
        return repo.getloginExceptionLiveData()
    }
}