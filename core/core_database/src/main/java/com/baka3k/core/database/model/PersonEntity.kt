package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baka3k.core.model.Person

@Entity(
    tableName = "person",
)
data class PersonEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(defaultValue = "false") val adult: Boolean,
    @ColumnInfo(defaultValue = "") val biography: String,
    @ColumnInfo(defaultValue = "") val alsoKnowAs: String,
    @ColumnInfo(defaultValue = "") val birthday: String,
    @ColumnInfo(defaultValue = "") val deathday: String,
    @ColumnInfo(defaultValue = "") val gender: Int,
    @ColumnInfo(defaultValue = "") val homepage: String,
    @ColumnInfo(defaultValue = "") val imdbId: String,
    @ColumnInfo(defaultValue = "") val knownForDepartment: String,
    @ColumnInfo(defaultValue = "") val name: String,
    @ColumnInfo(defaultValue = "") val placeOfBirth: String,
    @ColumnInfo(defaultValue = "0.0") val popularity: Double,
    @ColumnInfo(defaultValue = "") val profilePath: String,
)

fun PersonEntity.asExternalModel() = Person(
    id = id,
    adult = adult,
    biography = biography,
    birthday = birthday,
    deathday = deathday,
    gender = gender,
    homepage = homepage,
    imdbID = imdbId,
    knownForDepartment = knownForDepartment,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profilePath = profilePath,
    alsoKnownAs = buildAlsoKnowAs(alsoKnowAs)
)

fun buildAlsoKnowAs(alsoKnowAs: String): List<String> {
    if (alsoKnowAs.isEmpty()) {
        return emptyList()
    }
    else{
        return if (alsoKnowAs.contains("|")) {
            val temp = alsoKnowAs.split("|")
            if (temp.isNotEmpty()) {
                return temp.filter { it.isNotEmpty() }
            }
            return emptyList()
        } else {
            listOf(alsoKnowAs)
        }
    }
}