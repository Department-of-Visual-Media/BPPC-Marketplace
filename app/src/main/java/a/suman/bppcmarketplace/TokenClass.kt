package a.suman.bppcmarketplace

import android.app.Application
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

object TokenClass {
    var token: String? = null
    fun updateToken(application: Application): Completable {
        val authenticationServices =
            BPPCDatabase.getBPPCDatabase(application).getAuthenticationServices()
        return authenticationServices.getBasicUserData().subscribeOn(Schedulers.io())
            .flatMapCompletable { Completable.complete().doOnComplete { token = it[0]!!.token } }
    }
}


