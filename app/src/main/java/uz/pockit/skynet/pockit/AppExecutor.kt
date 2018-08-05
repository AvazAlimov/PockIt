package uz.pockit.skynet.pockit

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutor private constructor(diskIO: Executor, mainThread: Executor) {
    private var mDiskIO: Executor = diskIO
    private var mMainTread: Executor = mainThread

    constructor() : this(Executors.newSingleThreadExecutor(), MainThreadExecutor())

    fun diskIO(): Executor = mDiskIO
    fun mainThread(): Executor = mMainTread

    companion object {
        private class MainThreadExecutor : Executor {
            var mainThreadHandler: Handler = Handler(Looper.getMainLooper())
            override fun execute(command: Runnable) {
                mainThreadHandler.post(command)
            }
        }
    }
}
