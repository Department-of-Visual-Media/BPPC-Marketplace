package a.suman.bppcmarketplace.ProductList.Model.Network

import a.suman.bppcmarketplace.ApolloConnector
import a.suman.bppcmarketplace.BPPCDatabase
import a.suman.bppcmarketplace.TokenClass
import androidx.paging.PageKeyedDataSource
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.request.RequestHeaders
import com.apollographql.apollo.rx2.Rx2Apollo
import com.example.bppcmarketplace.GetProductListQuery
import com.example.bppcmarketplace.GetProfileQuery
import io.reactivex.schedulers.Schedulers

class ProductDataSource:PageKeyedDataSource<Long, ProductDataClass>(){
    private val apolloCliet =ApolloConnector.setUpApollo()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, ProductDataClass>) {

        Rx2Apollo.from(apolloCliet.query(GetProductListQuery(page = 1))).subscribeOn(Schedulers.io()).subscribe {  }

    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ProductDataClass>) {

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ProductDataClass>) {

    }


}