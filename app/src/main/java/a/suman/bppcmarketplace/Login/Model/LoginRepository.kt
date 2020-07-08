package a.suman.bppcmarketplace.Login.Model

import a.suman.bppcmarketplace.ApolloConnector
import a.suman.bppcmarketplace.BPPCDatabase
import a.suman.bppcmarketplace.BasicUserData
import a.suman.bppcmarketplace.Login.Model.Network.RetrofitClient
import a.suman.bppcmarketplace.Profile.Model.UserProfileDataClass
import a.suman.bppcmarketplace.R
import android.app.Application
import android.content.Intent
import android.util.Log
import com.apollographql.apollo.rx2.Rx2Apollo
import com.example.bppcmarketplace.MyProfileQuery
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginRepository(val application: Application) {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val apiInstance = RetrofitClient.instance!!.api

    private val bppcDatabase = BPPCDatabase.getBPPCDatabase(application)

    private val authenticationService = bppcDatabase.getAuthenticationServices()

    private val profileDao = bppcDatabase.getProfileDao()
    private val compositeDisposable = CompositeDisposable()


    fun getGoogleSignInIntent(): Intent {

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.OAuth2_client_id))
                .requestEmail().requestProfile().requestScopes(
                    Scope(Scopes.EMAIL), Scope(Scopes.PROFILE)
                )
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)

        return mGoogleSignInClient.signInIntent
    }


    fun googleSignInComplete(data: Intent?): Completable {
        try {
            val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                Log.d("REPO", "${account.displayName}")
                val googleSignInToken = account.idToken.toString()
                return apiInstance.authWithBackend(googleSignInToken).subscribeOn(Schedulers.io())
                    .flatMapCompletable {
//                    val auth=FirebaseAuth.getInstance()
//                   auth.signInAnonymously()
                        authenticationService.insertBasicUserData(it)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread()).doOnComplete {
                            Log.i("User Info", it.toString())
                            saveUserProfile(it.token)

                        }
                    }
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            Log.d("REPO", "$e")
            return Completable.error(e)
        }
    }

    fun observeForToken(): Single<List<BasicUserData?>?> {
        return authenticationService.getBasicUserData().subscribeOn(Schedulers.io())
    }

    fun logOut(): Completable {
        return Completable.mergeArray(
            authenticationService
            .removeBasicUserData()
            .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()), profileDao.removeUserProfile()
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        )
    }


    private fun saveUserProfile(token: String) {
        compositeDisposable.add(
            Rx2Apollo.from(
                ApolloConnector.setUpApollo().query(
                    MyProfileQuery()
                )
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (it.hasErrors()) {
                    Log.i("Profile Response Error", it.errors().toString())
                } else if (it.data()?.myProfile != null) {

                    profileDao.insertUserProfile(
                        UserProfileDataClass(
                            it.data()!!.myProfile!!.email,
                            it.data()!!.myProfile!!.name,
                            it.data()!!.myProfile!!.hostel,
                            it.data()!!.myProfile!!.contactNo,
                            it.data()!!.myProfile!!.roomNo
                        )
                    ).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe {
                            Log.i("Login Repository", "ProfileSaved")

                        }
                }
            }, {
                Log.i("Saving Into DB failed", it.message.toString())
            })
        )

    }

}


