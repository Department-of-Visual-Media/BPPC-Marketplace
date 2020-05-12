package a.suman.bppcmarketplace.Profile.ViewModel

import a.suman.bppcmarketplace.ProductList.Model.Network.ProductDataClass
import a.suman.bppcmarketplace.Profile.Model.ProfileRepository
import a.suman.bppcmarketplace.Profile.Model.UserProfileDataClass
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.bppcmarketplace.ProfileWithProductsQuery
import io.reactivex.disposables.CompositeDisposable

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val profileRepository = ProfileRepository(application)
    private val compositeDisposable = CompositeDisposable()
    private val mutableLiveData = MutableLiveData<UserProfileDataClass>()
    val profileLiveData = liveData { emitSource(mutableLiveData) }

    fun getCachedProfile() {
        compositeDisposable.add(profileRepository.getCachedProfile().subscribe({
            if (it.isNotEmpty()) {
                mutableLiveData.postValue(it[0])
                Log.i("test log", it[0]!!.name)
            }
        }, {}))
        updateProfile()
    }

    private fun updateProfile() {
        compositeDisposable.add(profileRepository.getProfileObservable().subscribe {
            if (it.hasErrors()) {
                Log.i("Profile Response Error", it.errors().toString())
            } else if (it.data()?.myProfile() != null) {
                mutableLiveData.postValue(
                    UserProfileDataClass(
                        it.data()!!.myProfile()!!.email(),
                        it.data()!!.myProfile()!!.name(),
                        it.data()!!.myProfile()!!.hostel(),
                        it.data()!!.myProfile()!!.contactNo(),
                        it.data()!!.myProfile()!!.roomNo(),
                        convertProductList(
                            it.data()!!.myProfile()!!.products()
                                    as List<ProfileWithProductsQuery.Product>
                        )
                    )
                )
            }
        })
    }

    private fun convertProductList(list: List<ProfileWithProductsQuery.Product>): List<ProductDataClass> {
        val i = 0
        val productList = ArrayList<ProductDataClass>()
        while (i < list.size) {
            productList.add(ProductDataClass(list[i].name(), list[i].sold()))
        }
        return productList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}