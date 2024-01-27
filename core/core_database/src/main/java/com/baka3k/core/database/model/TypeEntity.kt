package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baka3k.core.model.Type

@Entity(
    tableName = "type",
)
data class TypeEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(defaultValue = "") val title: String = "",
)

fun TypeEntity.asExternalModel() = Type(
    id = id,
    title = title,
)
object MovieType {
    const val POPULAR = 1L
    const val NOW_PLAYING = 2L
    const val TOP_RATE = 3L
    const val UP_COMMING = 4L
    const val OTHER = 5L
}