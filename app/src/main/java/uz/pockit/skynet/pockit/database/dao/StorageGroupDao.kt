package uz.pockit.skynet.pockit.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import uz.pockit.skynet.pockit.database.entity.StorageGroup

@Dao
interface StorageGroupDao{
    @get:Query("SELECT * FROM storage_group")
    val all: List<StorageGroup>
}