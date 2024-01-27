package com.baka3k.core.database.util

import androidx.room.TypeConverter
import com.baka3k.core.model.NewsResourceType
import com.baka3k.core.model.asNewsResourceType
import kotlinx.datetime.Instant


class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}

class NewsResourceTypeConverter {
    @TypeConverter
    fun newsResourceTypeToString(value: NewsResourceType?): String? =
        value?.let(NewsResourceType::serializedName)

    @TypeConverter
    fun stringToNewsResourceType(serializedName: String?): NewsResourceType =
        serializedName.asNewsResourceType()
}
