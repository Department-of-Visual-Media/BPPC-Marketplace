package a.suman.bppcmarketplace


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface AuthenticationServices{
    @Query("SELECT * FROM BasicUserData")
    fun getBasicUserData(): Single<List<BasicUserData?>?>

    @Query("DELETE FROM BasicUserData")
    fun removeBasicUserData():Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBasicUserData(u:BasicUserData):Completable

}