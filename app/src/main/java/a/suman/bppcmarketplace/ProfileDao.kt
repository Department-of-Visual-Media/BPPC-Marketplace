package a.suman.bppcmarketplace

import a.suman.bppcmarketplace.Profile.Model.UserProfileDataClass
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProfileDao {
    @Query("SELECT * FROM UserProfileDataClass")
    fun getUserProfile(): Single<List<UserProfileDataClass?>>

    @Query("DELETE FROM UserProfileDataClass")
    fun removeUserProfile(): Completable

    @Insert
    fun insertUserProfile(u: UserProfileDataClass): Completable
}