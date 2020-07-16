package a.suman.bppcmarketplace.Profile.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProfileDataClass(
    @PrimaryKey
    var email: String,
    var name: String,
    var hostel: String?,
    var contactNo: String?
)

@Entity
data class UserProductClass(
    var name: String,
    var basePrice: Int,
    var description: String?,
    var sold: String
)


