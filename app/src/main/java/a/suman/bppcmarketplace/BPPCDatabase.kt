package a.suman.bppcmarketplace


import a.suman.bppcmarketplace.Profile.Model.ProfileDao
import a.suman.bppcmarketplace.Profile.Model.UserProfileDataClass
import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BasicUserData::class, UserProfileDataClass::class],
    version = 1,
    exportSchema = false
)
abstract class BPPCDatabase:RoomDatabase(){
    abstract fun getAuthenticationServices(): AuthenticationServices
    abstract fun getProfileDao(): ProfileDao



    companion object {
        @Volatile
        private var INSTANCE: BPPCDatabase? = null

        @Synchronized
        fun getBPPCDatabase(application: Application): BPPCDatabase {
            var instance = INSTANCE
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            application.applicationContext,
                            BPPCDatabase::class.java,
                            "BPPCDatabase"
                        ).build()
                        INSTANCE = instance
                    }
                }
            }
            return instance!!
        }
    }

}