package com.baka3k.core.database

import com.baka3k.core.database.dao.CastDao
import com.baka3k.core.database.dao.CrewDao
import com.baka3k.core.database.dao.GenreDao
import com.baka3k.core.database.dao.MovieDao
import com.baka3k.core.database.dao.MovieGenreDao
import com.baka3k.core.database.dao.MovieTypeDao
import com.baka3k.core.database.dao.PersonDao
import com.baka3k.core.database.dao.ReviewDao
import com.baka3k.core.database.dao.TypeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesMovieDao(
        database: HiDatabase,
    ): MovieDao = database.movieDao()

    @Provides
    fun providesTypeDao(
        database: HiDatabase,
    ): TypeDao = database.typeDao()

    @Provides
    fun providesMovieTypeDao(
        database: HiDatabase,
    ): MovieTypeDao = database.movieTypeDao()

    @Provides
    fun providesCrewDao(
        database: HiDatabase,
    ): CrewDao = database.crewDao()

    @Provides
    fun providesCastDao(
        database: HiDatabase,
    ): CastDao = database.castDao()

    @Provides
    fun providesGenreDao(
        database: HiDatabase,
    ): GenreDao = database.genreDao()

    @Provides
    fun providesMovieGenreDao(
        database: HiDatabase,
    ): MovieGenreDao = database.movieGenreDao()

    @Provides
    fun providesPersonDao(
        database: HiDatabase,
    ): PersonDao = database.personDao()

    @Provides
    fun providesReviewDao(
        database: HiDatabase,
    ): ReviewDao = database.reviewDao()
}
