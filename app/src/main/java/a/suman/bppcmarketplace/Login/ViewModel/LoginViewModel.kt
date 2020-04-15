package a.suman.bppcmarketplace.Login.ViewModel

import a.suman.bppcmarketplace.Login.Model.LoginRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

public class LoginViewModel(application: Application):AndroidViewModel(application){


    var repo: LoginRepository =
        LoginRepository(application) // Class name of Repository is LoginRepo


    fun callAndSaveInShared(){
        repo.callAndSaveinSharedPref() // Method name for repo
    }

    fun getFromShared(){
        repo.getFromSharedPref() // Method name for repo
    }
}