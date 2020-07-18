package a.suman.bppcmarketplace.Upload.ViewModel

import a.suman.bppcmarketplace.ApolloConnector
import a.suman.bppcmarketplace.Upload.Model.UploadRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import io.reactivex.disposables.CompositeDisposable

class UploadViewModel(application: Application) : AndroidViewModel(application) {
    private val uploadRepository = UploadRepository()
    private val compositeDisposable = CompositeDisposable()
    private val postResultMutableLiveData = MutableLiveData<PostResult>()
    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    val postResultLiveData = liveData { emitSource(postResultMutableLiveData) }
    val isLoadingLiveData = liveData { emitSource(isLoadingMutableLiveData) }

    enum class PostResult {
        Successful,
        Unsuccessful,
        Error
    }

    fun addProduct(name: String, description: String, price: Int, isNegotiable: Boolean) {
        isLoadingMutableLiveData.postValue(true)
        compositeDisposable.add(
            uploadRepository.addProduct(name, description, price, isNegotiable).subscribe({
                if (it.data() != null) {
                    if (it.data()!!.createProduct!!.ok)
                        postResultMutableLiveData.postValue(PostResult.Successful)
                    else
                        postResultMutableLiveData.postValue(PostResult.Unsuccessful)
                }
                isLoadingMutableLiveData.postValue(false)

            }, {
                Log.i("Add Product Failed", it.toString());
                postResultMutableLiveData.postValue(PostResult.Error)
                isLoadingMutableLiveData.postValue(false)

            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        ApolloConnector.invalidateApollo()
    }
}