package a.suman.bppcmarketplace.ProductList.Model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.DataSource
import com.example.bppcmarketplace.GetProductListQuery
import io.reactivex.disposables.CompositeDisposable


class ProductDataFactory : DataSource.Factory<Int, GetProductListQuery.Object>() {

    private val error = MutableLiveData<List<String?>>()

    val errorState = liveData { emitSource(error) }
    var compositeDisposable = CompositeDisposable()

    override fun create(): DataSource<Int, GetProductListQuery.Object> {
        val productDataSource = ProductDataSource()
        error.postValue(productDataSource.errorState.value)
        compositeDisposable = productDataSource.compositeDisposable
        return ProductDataSource()
    }

}
