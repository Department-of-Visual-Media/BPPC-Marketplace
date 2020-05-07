package a.suman.bppcmarketplace.Profile.ViewModel

import a.suman.bppcmarketplace.Profile.Model.ProfileRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val profileRepository = ProfileRepository(application)
    val profileLiveData = liveData { emitSource(profileRepository.profileLiveData) }

    fun fetchProfile() {
        profileRepository.fetchProfile()
    }

    fun dispose() {
        profileRepository.dispose()
    }
}