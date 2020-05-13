package a.suman.bppcmarketplace.Profile.Model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.bppcmarketplace.ProfileWithProductsQuery

@Entity
data class UserProfileDataClass(
    @PrimaryKey
    var email: String,
    var name: String,
    var hostel: String?,
    var contactNo: Int?,
    var roomNo: Int?,
    @Ignore
    var productList: List<ProfileWithProductsQuery.Product>?

) {
    constructor(email: String, name: String, hostel: String?, contactNo: Int?, roomNo: Int?)
            : this(email, name, hostel, contactNo, roomNo, null)
}

