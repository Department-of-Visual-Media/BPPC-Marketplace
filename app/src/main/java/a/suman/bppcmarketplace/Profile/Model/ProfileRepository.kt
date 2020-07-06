package a.suman.bppcmarketplace.Profile.Model


import a.suman.bppcmarketplace.ApolloConnector
import a.suman.bppcmarketplace.BPPCDatabase
import android.app.Application
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.example.bppcmarketplace.ProfileWithProductsQuery
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileRepository(application: Application) {
    private val appDatabase = BPPCDatabase.getBPPCDatabase(application)

    fun getCachedProfile(): Single<List<UserProfileDataClass?>> {
        return appDatabase.getProfileDao().getUserProfile()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getProfileObservable(): Observable<Response<ProfileWithProductsQuery.Data>> {
        return appDatabase.getAuthenticationServices().getBasicUserData()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapObservable {
                getApolloClient(it[0]!!.token)
            }
    }

    private fun getApolloClient(token: String): Observable<Response<ProfileWithProductsQuery.Data>> {
        return Rx2Apollo.from(
            ApolloConnector.setUpApollo().query(
                ProfileWithProductsQuery()
            )
        )
    }
}