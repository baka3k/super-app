package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baka3k.core.model.Review

@Entity(
    tableName = "review",
)
data class ReviewEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(defaultValue = "") val authorName: String = "",
    @ColumnInfo(defaultValue = "") val authorUserName: String = "",
    @ColumnInfo(defaultValue = "") val avatarPath: String = "",
    @ColumnInfo(defaultValue = "0") val rating: Int = 0,
    @ColumnInfo(defaultValue = "") val content: String = "",
    @ColumnInfo(defaultValue = "") val createdAt: String = "",
    @ColumnInfo(defaultValue = "") val updatedAt: String = "",
    @ColumnInfo(defaultValue = "") val url: String = "",
    @ColumnInfo(defaultValue = "0") val movie_id: Long = 0,
)

fun ReviewEntity.asExternalModel() = Review(
    authorName = authorName,
    authorUserName = authorUserName,
    avatarPath = avatarPath,
    rating = rating,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
    url = url,
)
