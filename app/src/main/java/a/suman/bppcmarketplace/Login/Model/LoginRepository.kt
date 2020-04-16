package a.suman.bppcmarketplace.Login.Model

import a.suman.bppcmarketplace.R
import android.app.Application
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LoginRepository(application: Application) {
    private val RC_SIGN_IN = 1
    lateinit private var mGoogleSignInClient: GoogleSignInClient
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var token: String


    fun getGoogleSignInIntent(): Intent {

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.OAuth2_client_id))
                .requestEmail().requestProfile().requestScopes(
                    Scope(Scopes.EMAIL), Scope(Scopes.PROFILE)
                )
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        Log.i(TAG, "getting intent")
        return signInIntent
    }

    var application: Application

    init {
        this.application = application
    }


    fun googleSignInComplete(data: Intent?) {
        try {
            Log.i(TAG, "Account recieved in repo")
            val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                Log.i(Companion.TAG, "${account.displayName} ${account.email}")
                token = account.idToken.toString()
                val observable = RetrofitClient.instance!!.api.registerWithBackend(token)
                // val authObservable: Observable<LoginResponse> =
                //  RetrofitClient.instance!!.api.authWithBackend(token)
//                compositeDisposable
//                    .add(authObservable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(object :DisposableObserver<LoginResponse>(){
//                            override fun onComplete() {
//                                Log.i(TAG,"User Logged In")
//                            }
//
//                            override fun onNext(t: LoginResponse) {
//                                Log.i(TAG,t.token+" "+t.isNew)
//                            }
//
//                            override fun onError(e: Throwable) {
//                                Log.i(TAG+"Error",e.localizedMessage)
//                            }
//
//                        }))

                compositeDisposable.add(
                    observable.subscribeOn(Schedulers.io()).observeOn(
                        AndroidSchedulers.mainThread()
                    ).subscribe({

                    }
                        , {
                            if (it is HttpException) {
                                val observable2 =
                                    RetrofitClient.instance!!.api.loginWithBackend(token)
                                compositeDisposable.add(
                                    observable2.observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(
                                            Schedulers.io()
                                        ).subscribe({
                                            Log.i(TAG, "\n ${it.token}")
                                            storeDataIntoSharedPref(
                                                TOKEN_TAG,
                                                it.token,
                                                application
                                            )
                                        }, {
                                        })
                                )
                            }
                        })
                )
            }


        } catch (e: ApiException) {
            Log.i(TAG, e.toString())

        }
    }

    fun getTokenFromShared() {
        getDataFromSharedPref(TOKEN_TAG, application)
    }

    companion object {
        const val TAG: String = "LoginRepository"
        const val TOKEN_TAG: String = "token"
    }

    fun storeDataIntoSharedPref(
        key: String?,
        value: String?,
        application: Application
    ) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
        Log.i(TAG + "ShPref", "Data Saved")
    }


    fun getDataFromSharedPref(
        key: String?,
        application: Application
    ): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        return preferences.getString(key, null)
    }

    fun clearDisposables() {
        compositeDisposable.clear()
    }

}