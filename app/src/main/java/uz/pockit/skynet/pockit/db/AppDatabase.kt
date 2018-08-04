package uz.pockit.skynet.pockit.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import uz.pockit.skynet.pockit.db.dao.StorageGroupDao
import uz.pockit.skynet.pockit.db.entity.StorageGroup

@Database(entities = [(StorageGroup::class)], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storageGroupDao(): StorageGroupDao

    companion object {
        private var sInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room
                        .databaseBuilder(context.applicationContext, AppDatabase::class.java, "database")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return sInstance!!
        }
    }
}