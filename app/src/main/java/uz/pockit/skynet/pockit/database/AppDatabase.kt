package uz.pockit.skynet.pockit.database

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import uz.pockit.skynet.pockit.AppExecutor
import uz.pockit.skynet.pockit.database.dao.StorageGroupDao
import uz.pockit.skynet.pockit.database.entity.StorageGroup

@Database(entities = [(StorageGroup::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storageGroupDao(): StorageGroupDao

    companion object {
        const val DATABASE_NAME: String = "database"
        private val mIsDatabaseCreated: MutableLiveData<Boolean> = MutableLiveData()
        private var sInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context, executors: AppExecutor): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class) {
                    if (sInstance == null) {
                        sInstance = buildDatabase(context.applicationContext, executors)
                        sInstance!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return sInstance!!
        }

        private fun buildDatabase(appContext: Context, executors: AppExecutor): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            executors.diskIO().execute {
                                addDelay()
                                val database: AppDatabase = getInstance(appContext, executors)
                                database.setDatabaseCreated()
                            }
                        }
                    })
                    .build()
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }
        }

    }

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }
}