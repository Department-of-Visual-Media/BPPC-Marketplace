package a.suman.bppcmarketplace.Login.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

public class LoginViewModel(application: Application):AndroidViewModel(application){


    var repo: LoginRepo = LoginRepo(application) // Class name of Repository is LoginRepo


    fun callAndSaveInShared(){
        repo.callAndSaveinSharedPref() // Method name for repo
    }

    fun getFromShared(){
        repo.getFromSharedPref() // Method name for repo
    }
}