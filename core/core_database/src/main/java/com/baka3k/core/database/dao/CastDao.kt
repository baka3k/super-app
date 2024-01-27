package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.baka3k.core.database.model.CastEntity
import com.baka3k.core.database.model.CrewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CastDao {

    @Query(
        value = """
        SELECT * FROM `cast`
        WHERE movieId = :movieId
    """
    )
    fun getCast(movieId: Long): Flow<List<CastEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCast(castEntities: List<CastEntity>): List<Long>

    @Update
    suspend fun updateCast(entities: List<CastEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertCast(entities: List<CastEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreCast,
        updateMany = ::updateCast
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM `cast`
            WHERE id in (:ids)
        """
    )
    suspend fun deleteCast(ids: List<Int>)

    @Query(
        value = """
            SELECT COUNT(*) FROM `cast`

        """
    )
    suspend fun countCast(): Int
}