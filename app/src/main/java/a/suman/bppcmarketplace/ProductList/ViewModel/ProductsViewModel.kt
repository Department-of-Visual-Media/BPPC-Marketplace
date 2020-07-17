package a.suman.bppcmarketplace.ProductList.ViewModel

import a.suman.bppcmarketplace.ProductList.Model.ProductDataFactory
import a.suman.bppcmarketplace.ProductList.Model.ProductDataSource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.android.datatransport.runtime.logging.Logging.d

class ProductsViewModel : ViewModel() {
    var productFactory = ProductDataFactory()
    var pagedListConfig =
        PagedList.Config.Builder().setEnablePlaceholders(true).setInitialLoadSizeHint(10)
            .setPageSize(10).build()
    val errorState: MutableLiveData<List<String?>> = MutableLiveData()
    val observer = Observer<ProductDataSource> {
        d("ViewModel", "Received ProductDataSource")
        it.ErrorState.observeForever {
            d("ViewModel", "Received ErrorState")
            errorState.postValue(it)
        }
    }

    init {
        productFactory.productLiveData.observeForever(observer)
    }

    var pagedList = LivePagedListBuilder(productFactory, pagedListConfig).build()
    fun updatePagedList() {
        productFactory.productLiveData.removeObserver(observer)
        productFactory = ProductDataFactory()
        pagedListConfig =
            PagedList.Config.Builder().setEnablePlaceholders(true).setInitialLoadSizeHint(10)
                .setPageSize(10).build()
        productFactory.productLiveData.observeForever(observer)
        pagedList = LivePagedListBuilder(productFactory, pagedListConfig).build()

    }

    override fun onCleared() {
        productFactory.productLiveData.removeObserver(observer)
        productFactory.compositeDisposable.dispose()
        super.onCleared()
    }
}
