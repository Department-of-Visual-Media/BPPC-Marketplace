package a.suman.bppcmarketplace.Profile.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProfileDao {
    @Query("SELECT * FROM UserProfileDataClass")
    fun getUserProfile(): Single<List<UserProfileDataClass?>>

    @Query("DELETE FROM UserProfileDataClass")
    fun removeUserProfile(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProfile(u: UserProfileDataClass): Completable
}