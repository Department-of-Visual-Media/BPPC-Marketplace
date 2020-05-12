package a.suman.bppcmarketplace.Profile.Model

import a.suman.bppcmarketplace.ProductList.Model.Network.ProductDataClass
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class UserProfileDataClass(
    @PrimaryKey
    var email: String,
    var name: String,
    var hostel: String?,
    var contactNo: Int?,
    var roomNo: Int?,
    @Ignore
    var productList: List<ProductDataClass>?

) {
    constructor(email: String, name: String, hostel: String?, contactNo: Int?, roomNo: Int?)
            : this(email, name, hostel, contactNo, roomNo, null)
}

