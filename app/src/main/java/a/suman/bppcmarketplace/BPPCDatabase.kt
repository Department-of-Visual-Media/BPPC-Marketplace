package a.suman.bppcmarketplace

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BasicUserData::class], version = 1, exportSchema = false)
abstract class BPPCDatabase : RoomDatabase() {
    abstract fun getAuthenticationServices(): AuthenticationServices

    companion object {
        @Volatile
        private var INSTANCE: BPPCDatabase? = null

        @Synchronized
        fun getBPPCDatabase(application: Application): BPPCDatabase {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }

            val instance2 = Room.databaseBuilder(
                application.applicationContext,
                BPPCDatabase::class.java,
                "BPPCDatabase"
            ).build()
            INSTANCE = instance2
            return instance2

        }
    }

}