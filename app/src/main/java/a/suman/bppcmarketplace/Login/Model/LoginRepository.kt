package a.suman.bppcmarketplace.Login.Model

import android.app.Application

class LoginRepository(application: Application) {

    fun getFromSharedPref() {
        TODO("Not yet implemented")
    }

    fun callAndSaveinSharedPref() {
        TODO("Not yet implemented")
    }

    lateinit var application: Application

    init {
        this.application = application
    }

}