package a.suman.bppcmarketplace.Cart.WishList.Model

import a.suman.bppcmarketplace.BPPCDatabase
import a.suman.bppcmarketplace.Profile.Model.ApolloConnector
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import com.example.bppcmarketplace.GetWishListQuery

class WishListRepo(application: Application) {


    lateinit var token: String
    lateinit var disposable: Disposable
    val wishListMutableLiveData = MutableLiveData<WishListClass>()
    val appDatabase = BPPCDatabase.getBPPCDatabase(application)


     fun getWishListFromBack(): MutableLiveData<WishListClass> {

        disposable = appDatabase.getAuthenticationServices().getBasicUserData()
            .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                    if (it.size == 1) {
                        token = it[0]!!.token
                    }
                    ApolloConnector.setUpApollo().query(GetWishListQuery.builder().build())
                        .requestHeaders(
                            RequestHeaders.builder()
                                .addHeader("Authorization", "JWT $token").build()
                        )

                        .enqueue(object : ApolloCall.Callback<GetWishListQuery.Data>() {
                            override fun onFailure(e: ApolloException) {
                                Log.d("WishList", "Failed to receive")
                            }

                            override fun onResponse(response: Response<GetWishListQuery.Data>) {
                                if (response.data()?.wishlist() != null) {
                                    val my_wishlist =
                                        WishListClass(
                                            response.data()!!.wishlist()!!.name(),
                                            response.data()!!.wishlist()!!.basePrice(),
                                            response.data()!!.wishlist()!!.description(),
                                            response.data()!!.wishlist()!!.images(),
                                            response.data()!!.wishlist()!!.seller()

                                        )
                                    wishListMutableLiveData.postValue(my_wishlist)
                                }
                            }
                        }
                        )
                }
            )
        return wishListMutableLiveData
    }


    fun dispose() {
        disposable.dispose()
    }
}

