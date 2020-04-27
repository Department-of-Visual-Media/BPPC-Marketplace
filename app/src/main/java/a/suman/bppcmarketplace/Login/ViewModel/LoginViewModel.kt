package a.suman.bppcmarketplace.Login.ViewModel

import a.suman.bppcmarketplace.BasicUserData
import a.suman.bppcmarketplace.Login.Model.LoginRepository
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.liveData
import com.google.android.gms.common.api.ApiException
import io.reactivex.disposables.CompositeDisposable



class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val RC_SIGN_IN = 1
    private var repo: LoginRepository =
        LoginRepository(application)

    val compositeDisposable=CompositeDisposable()

    private val statusLiveData: MutableLiveData<String> = MutableLiveData()
    val loginStatus= liveData {emitSource(statusLiveData) }

    private val loginTokenMutable =MutableLiveData<BasicUserData>()
    val loginToken = liveData {emitSource(loginTokenMutable)  }

    init{
        compositeDisposable.add(repo.observeForToken().subscribe ({loginTokenMutable.postValue(it)},{ }))
    }

    fun initGoogleSignIn(): Intent {
        return repo.getGoogleSignInIntent()
    }

    fun onResultFromActivity(requestCode: Int, data: Intent?) {
        when (requestCode) {
            RC_SIGN_IN -> {
                compositeDisposable.add(repo.googleSignInComplete(data).subscribe({
                    statusLiveData.postValue("Success")
                }, {
                    if(it is ApiException){
                        statusLiveData.postValue("Internet")
                    }else{
                    statusLiveData.postValue("Error")
                    }
                }))
            }
            else->{
                statusLiveData.postValue("GoogleSignInError")
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}