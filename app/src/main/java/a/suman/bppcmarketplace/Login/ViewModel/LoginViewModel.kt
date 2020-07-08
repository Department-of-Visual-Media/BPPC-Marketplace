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
import retrofit2.HttpException


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val RC_SIGN_IN = 1
    private var repo: LoginRepository =
        LoginRepository(application)

    private val compositeDisposable = CompositeDisposable()

    private val statusLiveData: MutableLiveData<String> = MutableLiveData()
    val loginStatus= liveData {emitSource(statusLiveData) }

    private val loginTokenMutable =MutableLiveData<BasicUserData?>()
    val loginToken = liveData {emitSource(loginTokenMutable)  }



    fun initGoogleSignIn(): Intent {
        return repo.getGoogleSignInIntent()
    }

    fun onResultFromActivity(requestCode: Int, data: Intent?) {
        when (requestCode) {
            RC_SIGN_IN -> {
                compositeDisposable.add(repo.googleSignInComplete(data).subscribe({
                    statusLiveData.postValue("Success")
                   compositeDisposable.add(repo.observeForToken().subscribe({
                       if (it!!.isNotEmpty()) {
                           loginTokenMutable.postValue(it[0])
                       }
                   }, {}))
                }, {
                    Log.d("ViewModel", "$it")
                    if(it is ApiException){
                        if(it.statusCode==12500){
                            statusLiveData.postValue("BitsMail")
                        }
                        if(it.statusCode==7){
                        statusLiveData.postValue("Internet")
                        }
                    }else if(it is HttpException){
                        if(it.code()==500){
                            statusLiveData.postValue("Server Error")
                        }
                        statusLiveData.postValue("Error")
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