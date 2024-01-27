package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


/**
 * Cross reference for many to many relationship between [EpisodeEntity] and [AuthorEntity]
 */
@Entity(
    tableName = "episodes_authors",
    primaryKeys = ["episode_id", "author_id"],
    foreignKeys = [
        ForeignKey(
            entity = EpisodeEntity::class,
            parentColumns = ["id"],
            childColumns = ["episode_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["episode_id"]),
        Index(value = ["author_id"]),
    ],
)
data class EpisodeAuthorCrossRef(
    @ColumnInfo(name = "episode_id")
    val episodeId: String,
    @ColumnInfo(name = "author_id")
    val authorId: String,
)
