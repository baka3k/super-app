package com.baka3k.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baka3k.core.database.dao.CastDao
import com.baka3k.core.database.dao.CrewDao
import com.baka3k.core.database.dao.GenreDao
import com.baka3k.core.database.dao.MovieDao
import com.baka3k.core.database.dao.MovieGenreDao
import com.baka3k.core.database.dao.MovieTypeDao
import com.baka3k.core.database.dao.PersonDao
import com.baka3k.core.database.dao.ReviewDao
import com.baka3k.core.database.dao.TypeDao
import com.baka3k.core.database.model.CastEntity
import com.baka3k.core.database.model.CrewEntity
import com.baka3k.core.database.model.GenreEntity
import com.baka3k.core.database.model.MovieEntity
import com.baka3k.core.database.model.MovieGenreCrossRef
import com.baka3k.core.database.model.MovieTypeCrossRef
import com.baka3k.core.database.model.PersonEntity
import com.baka3k.core.database.model.ReviewEntity
import com.baka3k.core.database.model.TypeEntity
import com.baka3k.core.database.util.InstantConverter
import com.baka3k.core.database.util.NewsResourceTypeConverter

@Database(
    entities = [
        MovieEntity::class,
        TypeEntity::class,
        MovieTypeCrossRef::class,
        CastEntity::class,
        CrewEntity::class,
        GenreEntity::class,
        MovieGenreCrossRef::class,
        PersonEntity::class,
        ReviewEntity::class,
    ],
    version = 9,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
//        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//        AutoMigration(from = 3, to = 4),
//        AutoMigration(from = 4, to = 5),
//        AutoMigration(from = 5, to = 6),
//        AutoMigration(from = 6, to = 7),
//        AutoMigration(from = 7, to = 8),
//        AutoMigration(from = 8, to = 9),
    ],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    NewsResourceTypeConverter::class,
)
abstract class HiDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun typeDao(): TypeDao
    abstract fun movieTypeDao(): MovieTypeDao
    abstract fun crewDao(): CrewDao
    abstract fun castDao(): CastDao
    abstract fun genreDao(): GenreDao
    abstract fun movieGenreDao(): MovieGenreDao
    abstract fun personDao(): PersonDao
    abstract fun reviewDao(): ReviewDao
}