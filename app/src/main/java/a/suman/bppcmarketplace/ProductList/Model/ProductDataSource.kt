package a.suman.bppcmarketplace.ProductList.Model

import a.suman.bppcmarketplace.ApolloConnector
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.rx2.Rx2Apollo
import com.example.bppcmarketplace.GetProductListQuery
import com.google.android.datatransport.runtime.logging.Logging.d
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketException

class ProductDataSource : PageKeyedDataSource<Int, GetProductListQuery.Object>() {
    private val apolloCliet = ApolloConnector.setUpApollo()
    val ErrorState = MutableLiveData<List<String?>>()
    var retry: (() -> Any)? = null
    val compositeDisposable = CompositeDisposable()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetProductListQuery.Object>
    ) {
        d("DataSource", "Initial")
        compositeDisposable.add(
            Rx2Apollo.from(apolloCliet.query(GetProductListQuery(page = 1, pagesize = 10)))
                .subscribeOn(Schedulers.io()).subscribe({
                    // d("Return", it.errors()[0].message())
                    if (it.data() != null) {
                        if (it.data()!!.products != null) {
                            if (it.data()!!.products!!.hasNext!!) {
                                callback.onResult(it.data()!!.products!!.objects!!, null, 2)
                                return@subscribe
                            } else {
                                callback.onResult(it.data()!!.products!!.objects!!, null, null)
                                return@subscribe
                            }
                        } else {
                            ErrorState.postValue(listOf("NoData"))
                        }
                    } else {
                        if (it.hasErrors()) {
                            ErrorState.postValue(it.errors().map { return@map it.message() })
                        } else {
                            ErrorState.postValue((listOf("NoData")))
                        }
                    }

                }, {
                    d("DataSource", it.toString())

                    if (it is SocketException || it is ApolloNetworkException) {
                        d("Error", it.toString())
                        ErrorState.postValue(listOf("NoInternet"))
                    }
                    loadInitial(params, callback)
                })
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetProductListQuery.Object>
    ) {
        compositeDisposable.add(
            Rx2Apollo.from(apolloCliet.query(GetProductListQuery(page = params.key, pagesize = 10)))
                .subscribeOn(Schedulers.io()).subscribe({
                    if (it.data() != null) {
                        if (it.data()!!.products != null) {
                            if (it.data()!!.products!!.hasNext!!) {
                                callback.onResult(it.data()!!.products!!.objects!!, params.key + 1)
                                return@subscribe
                            } else {
                                callback.onResult(it.data()!!.products!!.objects!!, null)
                                return@subscribe
                            }
                        } else {
                            ErrorState.postValue(listOf("NoData"))
                        }
                    } else {
                        if (it.hasErrors()) {
                            ErrorState.postValue(it.errors().map { return@map it.message() })
                        } else {
                            ErrorState.postValue((listOf("NoData")))
                        }
                    }

                }, {
                    d("DataSource2", it.toString())

                    if (it is SocketException || it is ApolloNetworkException) {
                        d("Error", it.toString())
                        ErrorState.postValue(listOf("NoInternet"))
                    }
                    loadAfter(params, callback)
                })
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetProductListQuery.Object>
    ) {
    }
}