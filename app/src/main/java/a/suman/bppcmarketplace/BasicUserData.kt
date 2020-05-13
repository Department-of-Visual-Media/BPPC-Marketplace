package a.suman.bppcmarketplace

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BasicUserData")
data class BasicUserData(
    @PrimaryKey
    var token: String,
    var name: String?,
    var email: String?,
    var isNew: Boolean
)