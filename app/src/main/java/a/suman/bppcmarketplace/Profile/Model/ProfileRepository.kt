package a.suman.bppcmarketplace.Profile.Model


import a.suman.bppcmarketplace.BPPCDatabase
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import com.example.bppcmarketplace.GetProfileQuery
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class ProfileRepository(application: Application) {
    lateinit var token: String
    lateinit var email: String
    lateinit var disposable: Disposable
    private val profileMutableLiveData = MutableLiveData<UserProfile>()
    val profileLiveData = liveData { emitSource(profileMutableLiveData) }
    val appDatabase = BPPCDatabase.getBPPCDatabase(application)


    private fun getUserProfileFromServer() {

        disposable = appDatabase.getAuthenticationServices().getBasicUserData()
            .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                    if (it.size == 1) {
                        token = it[0]!!.token
                        email = it[0]!!.email.toString()
                    }
                    ApolloConnector.setUpApollo().query(
                        GetProfileQuery.builder()
                            .email(email)
                            .build()
                    )
                        .requestHeaders(
                            RequestHeaders.builder()
                                .addHeader(
                                    "Authorization",
                                    //"JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjozLCJ1c2VybmFtZSI6ImYyMDE5MDExMiIsImV4cCI6MTU4NzcxNTAzNywiZW1haWwiOiJmMjAxOTAxMTJAcGlsYW5pLmJpdHMtcGlsYW5pLmFjLmluIn0.TYAcsBCv_hBykbLjTlEc0spKeKlrImu6NGwT-u3zwHc"
                                    "JWT $token"
                                )
                                .build()
                        )
                        .enqueue(object : ApolloCall.Callback<GetProfileQuery.Data>() {
                            override fun onFailure(e: ApolloException) {
                                Log.i("Fail", e.message)
                            }

                            override fun onResponse(response: Response<GetProfileQuery.Data>) {
                                if (response.hasErrors()) {
                                    Log.i("Response has Errors", response.errors().toString())
                                } else if (response.data()?.profile() != null) {
                                    val profile = UserProfile(
                                        Integer.parseInt(
                                            response.data()!!.profile()!!.id()
                                        ),
                                        response.data()!!.profile()!!.name(),
                                        response.data()!!.profile()!!.email(),
                                        response.data()!!.profile()!!.hostel(),
                                        response.data()!!.profile()!!.contactNo()
                                    )
                                    profileMutableLiveData.postValue(profile)

                                }
                            }
                        })
                })
    }

    fun fetchProfile() {
        getUserProfileFromServer()
    }

    fun dispose() {
        disposable.dispose()
    }

}