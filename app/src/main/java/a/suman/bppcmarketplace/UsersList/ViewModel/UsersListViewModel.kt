package a.suman.bppcmarketplace.UsersList.ViewModel

import a.suman.bppcmarketplace.UsersList.Model.UserDataFactory
import a.suman.bppcmarketplace.UsersList.Model.UserDataSource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.android.datatransport.runtime.logging.Logging.d

class UsersViewModel : ViewModel() {

    var userFactory = UserDataFactory()
    var pagedListConfig =
        PagedList.Config.Builder().setEnablePlaceholders(true).setInitialLoadSizeHint(10)
            .setPageSize(10).build()
    val errorState: MutableLiveData<List<String?>> = MutableLiveData()
    val observer = Observer<UserDataSource> {
        d("ViewModel", "Received ProductDataSource")
        it.ErrorState.observeForever {
            d("ViewModel", "Received ErrorState")
            errorState.postValue(it)
        }
    }

    init {
        userFactory.userLiveData.observeForever(observer)
    }

    var pagedList = LivePagedListBuilder(userFactory, pagedListConfig).build()

    fun updatePagedList() {
        userFactory.userLiveData.removeObserver(observer)
        userFactory = UserDataFactory()
        pagedListConfig =
            PagedList.Config.Builder().setEnablePlaceholders(true).setInitialLoadSizeHint(10)
                .setPageSize(10).build()
        userFactory.userLiveData.observeForever(observer)
        pagedList = LivePagedListBuilder(userFactory, pagedListConfig).build()

    }

    override fun onCleared() {
        userFactory.userLiveData.removeObserver(observer)
        userFactory.compositeDisposable.dispose()
        super.onCleared()
    }
}
