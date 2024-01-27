package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baka3k.core.database.model.GenreEntity
import com.baka3k.core.database.model.MovieEntity
import com.baka3k.core.database.model.MovieGenreCrossRef
import com.baka3k.core.database.model.MovieTypeCrossRef
import kotlinx.coroutines.flow.Flow

/**
 * Dao fpr cross reference for many to many relationship between [MovieEntity] and [GenreEntity]
 */
@Dao
interface MovieGenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreMovieGenre(movieGenreEntities: List<MovieGenreCrossRef>): List<Long>

    @Query(
        value = """
        SELECT * FROM genre INNER JOIN movie_genre 
            ON genre.id = movie_genre.genre_id
        WHERE movie_genre.movie_id = :movieId
    """
    )
    fun getGenreEntitiesStream(movieId: Long): Flow<List<GenreEntity>>

    @Query(
        value = """
            SELECT COUNT(*) FROM movie_genre

        """
    )
    suspend fun getCount(): Int
}