package a.suman.bppcmarketplace.ProductList.Model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.bppcmarketplace.GetProductListQuery
import com.google.android.datatransport.runtime.logging.Logging.d
import io.reactivex.disposables.CompositeDisposable


class ProductDataFactory : DataSource.Factory<Int, GetProductListQuery.Object>() {

    init {
        d("DataFactory", "NewFactory")
    }

    var productLiveData: MutableLiveData<ProductDataSource> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()

    override fun create(): DataSource<Int, GetProductListQuery.Object> {
        d("DataFactory", "Create")
        val productDataSource = ProductDataSource()
        productLiveData.postValue(productDataSource)
        compositeDisposable = productDataSource.compositeDisposable
        return productDataSource
    }

}
