package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.baka3k.core.database.model.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Query(
        value = """
        SELECT * FROM `review`
        WHERE movie_id = :movieId
    """
    )
    fun getMovieReviews(movieId: Long): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(entities: List<ReviewEntity>): List<Long>

    @Update
    suspend fun update(entities: List<ReviewEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsert(entities: List<ReviewEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnore,
        updateMany = ::update
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM `review`
            WHERE id in (:ids)
        """
    )
    suspend fun delete(ids: List<String>)

    @Query(
        value = """
            SELECT COUNT(*) FROM `review`

        """
    )
    suspend fun count(): Int
}