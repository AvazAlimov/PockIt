package uz.pockit.skynet.pockit.database.entity

import android.arch.persistence.room.*

@Entity(tableName = "storage_group",
        foreignKeys = [(ForeignKey(
                entity = StorageGroup::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("parent_id"),
                onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["parent_id"])])
data class StorageGroup(@ColumnInfo(name = "name") var name: String,
                        @ColumnInfo(name = "visible") var visible: Boolean,
                        @ColumnInfo(name = "parent_id") var parentId: Int?) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}