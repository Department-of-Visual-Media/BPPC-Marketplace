package a.suman.bppcmarketplace.Profile.ViewModel

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
    private val profileMutableLiveData = MutableLiveData<UserProfileDataClass>()
    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    private val productMutableLiveData = MutableLiveData<List<ProfileWithProductsQuery.Product?>?>()
    val profileLiveData = liveData { emitSource(profileMutableLiveData) }
    val isLoadingLiveData = liveData { emitSource(isLoadingMutableLiveData) }
    val productLiveData = liveData { emitSource(productMutableLiveData) }

    fun getCachedProfile() {
        compositeDisposable.add(profileRepository.getCachedProfile().subscribe({
            if (it.isNotEmpty()) {
                profileMutableLiveData.postValue(it[0])
            }
        }, {}))
        updateProfile()
    }

    private fun updateProfile() {
        isLoadingMutableLiveData.postValue(true)
        compositeDisposable.add(profileRepository.getProfileObservable().subscribe {
            if (it.hasErrors()) {
                Log.i("Profile Response Error", it.errors().toString())
            } else if (it.data()?.myProfile != null) {
                profileMutableLiveData.postValue(
                    UserProfileDataClass(
                        it.data()!!.myProfile!!.email,
                        it.data()!!.myProfile!!.name,
                        it.data()!!.myProfile!!.hostel,
                        it.data()!!.myProfile!!.contactNo
                    )
                )
                productMutableLiveData.postValue(it.data()!!.myProfile!!.products)
                isLoadingMutableLiveData.postValue(false)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}