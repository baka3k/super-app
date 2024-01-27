package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


/**
 * Cross reference for many to many relationship between [MovieEntity] and [TypeEntity]
 */
@Entity(
    tableName = "movie_type",
    primaryKeys = ["movie_id", "type_id"],
//    foreignKeys = [
//        ForeignKey(
//            entity = MovieEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["movie_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = TypeEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["type_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//    ],
    indices = [
        Index(value = ["movie_id"]),
        Index(value = ["type_id"]),
    ],
)
data class MovieTypeCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "type_id")
    val typeId: Long,
)
