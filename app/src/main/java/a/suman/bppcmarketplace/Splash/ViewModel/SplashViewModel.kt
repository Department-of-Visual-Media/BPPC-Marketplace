package a.suman.bppcmarketplace.Splash.ViewModel

import a.suman.bppcmarketplace.BasicUserData
import a.suman.bppcmarketplace.Splash.Model.SplashRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData

class SplashViewModel (application:Application):AndroidViewModel(application){

    private val splashRepository= SplashRepository(application)
    private val authenticationMLiveData= MutableLiveData <BasicUserData?>()
    val authenticationLiveData= liveData{ emitSource(authenticationMLiveData)
    }
       val disposable = splashRepository.getAuthenitcation().subscribe({Log.d("Viewmodel", "subscribed")
            if (it.size == 1) {
                authenticationMLiveData.postValue(it[0])
            } else {
                authenticationMLiveData.postValue(null)
            }
        }, {})


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}