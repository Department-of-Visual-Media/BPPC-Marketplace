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
import com.example.bppcmarketplace.MyProfileQuery
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class ProfileRepository(application: Application) {
    private lateinit var token: String
    private lateinit var email: String
    private lateinit var disposable: Disposable
    private val profileMutableLiveData = MutableLiveData<UserProfileDataClass>()
    val profileLiveData = liveData { emitSource(profileMutableLiveData) }
    private val appDatabase = BPPCDatabase.getBPPCDatabase(application)

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
                        MyProfileQuery.builder()
                            .build()
                    )
                        .requestHeaders(
                            RequestHeaders.builder()
                                .addHeader(
                                    "Authorization",
                                    "JWT $token"
                                )
                                .build()
                        )
                        .enqueue(object : ApolloCall.Callback<MyProfileQuery.Data>() {
                            override fun onFailure(e: ApolloException) {
                                Log.i("Fail", e.message.toString())
                            }

                            override fun onResponse(response: Response<MyProfileQuery.Data>) {
                                if (response.hasErrors()) {
                                    Log.i("Response has Errors", response.errors().toString())
                                } else if (response.data()?.myProfile() != null) {

                                    val profile = UserProfileDataClass(
                                        response.data()!!.myProfile()!!.name(),
                                        response.data()!!.myProfile()!!.email(),
                                        response.data()!!.myProfile()!!.hostel(),
                                        response.data()!!.myProfile()!!.contactNo(),
                                        response.data()!!.myProfile()!!.roomNo()

                                    )
                                    profileMutableLiveData.postValue(profile)
                                }
                            }
                        })
                }
            )
    }

    fun fetchProfile() {
        getUserProfileFromServer()
    }

    fun dispose() {
        disposable.dispose()
    }

//    fun getProductList(list: List<MyProfileQuery.Product>): List<ProductDataClass> {
//        val i = 0
//        val productList = ArrayList<ProductDataClass>()
//        while (i < list.size) {
//            productList.add(ProductDataClass(list[i].name(), list[i].sold()))
//        }
//        return productList
//    }
}