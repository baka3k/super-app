package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.baka3k.core.database.model.MovieEntity
import com.baka3k.core.database.model.MovieType.NOW_PLAYING
import com.baka3k.core.database.model.MovieType.OTHER
import com.baka3k.core.database.model.MovieType.POPULAR
import com.baka3k.core.database.model.MovieType.TOP_RATE
import com.baka3k.core.database.model.MovieType.UP_COMMING
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Transaction
    @Query(
        value = """
            SELECT * FROM movies INNER JOIN movie_type ON movies.id = movie_type.movie_id
            WHERE movie_type.type_id = '$OTHER'
            ORDER BY movies.id  DESC
    """
    )
    fun findTempMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Transaction
    @Query(
        value = """
            SELECT * FROM movies INNER JOIN movie_type ON movies.id = movie_type.movie_id
            WHERE movie_type.type_id = '$POPULAR'
            ORDER BY movies.id  DESC
    """
    )
    fun getPolularMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Transaction
    @Query(
        value = """
            SELECT * FROM movies INNER JOIN movie_type ON movies.id = movie_type.movie_id
            WHERE movie_type.type_id = '$NOW_PLAYING'
            ORDER BY movies.id  DESC
    """
    )
    fun getNowPlayingEntitiesStream(): Flow<List<MovieEntity>>

    @Transaction
    @Query(
        value = """
            SELECT * FROM movies INNER JOIN movie_type ON movies.id = movie_type.movie_id
            WHERE movie_type.type_id = '$TOP_RATE'
            ORDER BY movies.id  DESC
    """
    )
    fun getTopRateMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Transaction
    @Query(
        value = """
            SELECT * FROM movies INNER JOIN movie_type ON movies.id = movie_type.movie_id
            WHERE movie_type.type_id = '$UP_COMMING'
            ORDER BY movies.id  DESC
    """
    )
    fun getUpCommingMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Query(
        value = """
        SELECT * FROM movies 
        WHERE id = :id
    """
    )
    fun getMovieEntity(id: Long): Flow<MovieEntity>

    @Query(value = "SELECT * FROM movies")
    fun getMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Query(
        value = """
        SELECT * FROM movies
        WHERE id IN (:ids)
    """
    )
    fun getMovieEntitiesStream(ids: Set<String>): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreMovie(movieEntities: List<MovieEntity>): List<Long>

    @Update
    suspend fun updateMovies(entities: List<MovieEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertMovie(entities: List<MovieEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreMovie,
        updateMany = ::updateMovies
    )

    @Transaction
    suspend fun upsertMovie(entities: List<MovieEntity>, movieType: Int) {
        upsert(
            items = entities,
            insertMany = ::insertOrIgnoreMovie,
            updateMany = ::updateMovies
        )
    }

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
             DELETE FROM movies WHERE movies.id in (SELECT movie_id From movie_type WHERE type_id = '$OTHER')
        """
    )
    suspend fun deleteTempMovie()

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM movies
            WHERE id in (:ids)
        """
    )
    suspend fun deleteMovie(ids: List<String>)

    @Query(
        value = """
            SELECT COUNT(*) FROM movies

        """
    )
    suspend fun getCount(): Int
}