package a.suman.bppcmarketplace.UsersList.Model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.bppcmarketplace.GetUserListQuery
import com.google.android.datatransport.runtime.logging.Logging.d
import io.reactivex.disposables.CompositeDisposable


class UserDataFactory : DataSource.Factory<Int, GetUserListQuery.Object>() {

    init {
        d("DataFactory", "NewFactory")
    }

    var userLiveData: MutableLiveData<UserDataSource> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()

    override fun create(): DataSource<Int, GetUserListQuery.Object> {
        d("DataFactory", "Create")
        val userDataSource = UserDataSource()
        userLiveData.postValue(userDataSource)
        compositeDisposable = userDataSource.compositeDisposable
        return userDataSource
    }

}
