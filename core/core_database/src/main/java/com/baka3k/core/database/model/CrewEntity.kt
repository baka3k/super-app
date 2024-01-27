package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baka3k.core.model.Crew

@Entity(
    tableName = "crew",
)
data class CrewEntity(
    @PrimaryKey
    val id: Long = 0,

    @ColumnInfo(defaultValue = "false")
    val adult: Boolean,

    @ColumnInfo(defaultValue = "-1")
    val gender: Int,

    @ColumnInfo(name = "known_for_department", defaultValue = "")
    val knownForDepartment: String = "",

    @ColumnInfo(defaultValue = "")
    val name: String,

    @ColumnInfo(name = "original_name", defaultValue = "")
    val originalName: String,

    @ColumnInfo(defaultValue = "")
    val popularity: String,

    @ColumnInfo(name = "profile_path", defaultValue = "")
    val profilePath: String,

    @ColumnInfo(defaultValue = "")
    val department: String,

    @ColumnInfo(defaultValue = "")
    val job: String,

    @ColumnInfo(name = "credit_id", defaultValue = "")
    val creditId: String,

    @ColumnInfo(defaultValue = "0")
    val movieId: Long,
)

fun CrewEntity.asExternalModel() = Crew(
    id = id,
    adult = adult,
    gender = gender,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    department = department,
    profilePath = profilePath,
    job = job,
    creditId = creditId,
    movieId = movieId,
)