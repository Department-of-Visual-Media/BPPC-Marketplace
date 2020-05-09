package a.suman.bppcmarketplace

import a.suman.bppcmarketplace.Login.Model.LoginRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: LoginRepository = LoginRepository(application)
    private val compositeDisposable = CompositeDisposable()

    private val loginTokenMutable = MutableLiveData<BasicUserData?>()
    val loginToken = liveData { emitSource(loginTokenMutable) }


    fun logOut() {
        compositeDisposable.add(repo.logOut().subscribe {
            loginTokenMutable.postValue(null)
        })
    }
}