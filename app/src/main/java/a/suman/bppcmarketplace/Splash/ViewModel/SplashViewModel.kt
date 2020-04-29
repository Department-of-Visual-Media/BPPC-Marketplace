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
    private val authenticationMLiveData= MutableLiveData <BasicUserData>()
    val authenticationLiveData= liveData{ emitSource(authenticationMLiveData) }
    private val disposable= splashRepository.getAuthenitcation().subscribe ({ authenticationMLiveData.postValue(it)},{ })


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}