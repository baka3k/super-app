package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.baka3k.core.model.Cast

@Entity(
    tableName = "cast",
)
data class CastEntity(
    @PrimaryKey
    val id: Long = 0,

    @ColumnInfo(defaultValue = "false")
    val adult: Boolean,

    @ColumnInfo(defaultValue = "-1")
    val gender: Int,

    @ColumnInfo(name = "known_for_department", defaultValue = "")
    val knownForDepartment: String,

    @ColumnInfo(defaultValue = "")
    val name: String,

    @ColumnInfo(name = "original_name", defaultValue = "")
    val originalName: String,

    @ColumnInfo(name = "profile_path",defaultValue = "")
    val profilePath: String,

    @ColumnInfo(defaultValue = "")
    val popularity: String,

    @ColumnInfo(name = "cast_id", defaultValue = "0")
    val castId: Int,

    @ColumnInfo(defaultValue = "")
    val character: String,

    @ColumnInfo(defaultValue = "0")
    val order: Int,

    @ColumnInfo(name = "credit_id", defaultValue = "")
    val creditId: String,

    @ColumnInfo(defaultValue = "0")
    val movieId: Long,
)

fun CastEntity.asExternalModel() = Cast(
    id = id,
    adult = adult,
    gender = gender,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,
    castId = castId,
    character = character,
    order = order,
    creditId = creditId,
    movieId = movieId
)
