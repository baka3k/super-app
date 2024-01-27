package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baka3k.core.model.Genre
import com.baka3k.core.model.Movie
import com.baka3k.core.model.Type

@Entity(
    tableName = "genre",
)
data class GenreEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(defaultValue = "")
    val name: String = "",
)

fun GenreEntity.asExternalModel() = Genre(
    id = id,
    name = name
)