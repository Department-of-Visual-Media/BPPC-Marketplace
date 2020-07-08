package a.suman.bppcmarketplace.ProductList.ViewModel

import a.suman.bppcmarketplace.ProductList.Model.ProductDataFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class ProductsViewModel : ViewModel() {

    val productFactory =
        ProductDataFactory()
    val pagedListConfig =
        PagedList.Config.Builder().setEnablePlaceholders(true).setInitialLoadSizeHint(10)
            .setPageSize(10).build()
    val errorState = liveData { emitSource(productFactory.errorState) }
    val productList = LivePagedListBuilder(productFactory, pagedListConfig).build()

    override fun onCleared() {
        productFactory.compositeDisposable.dispose()
        super.onCleared()
    }
}
