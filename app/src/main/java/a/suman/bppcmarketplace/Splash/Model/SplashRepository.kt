package a.suman.bppcmarketplace.Splash.Model

import a.suman.bppcmarketplace.BPPCDatabase
import a.suman.bppcmarketplace.BasicUserData
import android.app.Application
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashRepository(application: Application){

    private val authenticateServices=BPPCDatabase.getBPPCDatabase(application).getAuthenticationServices()

    fun getAuthenitcation(): Single<List<BasicUserData?>> {
        return authenticateServices.getBasicUserData().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
    }
}