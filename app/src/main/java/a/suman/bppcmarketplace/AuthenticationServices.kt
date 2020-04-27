package a.suman.bppcmarketplace


import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface AuthenticationServices{
    @Query("SELECT * FROM BasicUserData")
    fun getBasicUserData(): Flowable<BasicUserData>

    @Query("DELETE FROM BasicUserData")
    fun removeBasicUserData():Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBasicUserData(u:BasicUserData):Completable

}