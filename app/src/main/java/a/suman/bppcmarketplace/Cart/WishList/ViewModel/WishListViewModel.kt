package a.suman.bppcmarketplace.Cart.WishList.ViewModel

import a.suman.bppcmarketplace.Cart.WishList.Model.WishListClass
import a.suman.bppcmarketplace.Cart.WishList.Model.WishListRepo
import a.suman.bppcmarketplace.Cart.WishList.View.WishList
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

class WishListViewModel(application: Application):AndroidViewModel(application){

    val wishListRepo = WishListRepo(application)


    fun getWishList():LiveData<WishListClass>{
        val wishListLiveData = liveData { emitSource(wishListRepo.getWishListFromBack()) }
        return wishListLiveData
    }

    fun disposeFunction(){
        wishListRepo.dispose()
    }
}