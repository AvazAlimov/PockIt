package uz.pockit.skynet.pockit

import android.app.Application
import uz.pockit.skynet.pockit.database.AppDatabase

class BasicApp : Application() {

    private lateinit var mAppExecutor: AppExecutor

    override fun onCreate() {
        super.onCreate()
        mAppExecutor = AppExecutor()
    }

    fun getDatabase(): AppDatabase = AppDatabase.getInstance(this, mAppExecutor)
}