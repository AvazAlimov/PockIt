package uz.pockit.skynet.pockit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import uz.pockit.skynet.pockit.db.AppDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAsync {
            val database = AppDatabase.getInstance(context = this@MainActivity)
            val storageGroups = database.storageGroupDao().all

            uiThread {
                Toast.makeText(this@MainActivity, storageGroups.size.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
