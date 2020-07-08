package a.suman.bppcmarketplace.ProductList.Model

import a.suman.bppcmarketplace.ApolloConnector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.PageKeyedDataSource
import com.apollographql.apollo.rx2.Rx2Apollo
import com.example.bppcmarketplace.GetProductListQuery
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductDataSource : PageKeyedDataSource<Int, GetProductListQuery.Object>() {
    private val apolloCliet = ApolloConnector.setUpApollo()
    private val ErrorState = MutableLiveData<List<String?>>()
    val errorState = liveData { emitSource(ErrorState) }
    val compositeDisposable = CompositeDisposable()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetProductListQuery.Object>
    ) {
        compositeDisposable.add(
            Rx2Apollo.from(apolloCliet.query(GetProductListQuery(page = 1)))
                .subscribeOn(Schedulers.io()).subscribe {
                if (it.data() != null) {
                    if (it.data()!!.products != null) {
                        if (it.data()!!.products!!.hasNext!!) {
                            callback.onResult(it.data()!!.products!!.objects!!, null, 2)
                        } else {
                            callback.onResult(it.data()!!.products!!.objects!!, null, null)
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
            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetProductListQuery.Object>
    ) {
        compositeDisposable.add(
            Rx2Apollo.from(apolloCliet.query(GetProductListQuery(page = params.key)))
                .subscribeOn(Schedulers.io()).subscribe {
                if (it.data() != null) {
                    if (it.data()!!.products != null) {
                        if (it.data()!!.products!!.hasNext!!) {
                            callback.onResult(it.data()!!.products!!.objects!!, params.key + 1)
                        } else {
                            callback.onResult(it.data()!!.products!!.objects!!, null)
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
            })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetProductListQuery.Object>
    ) {
    }
}