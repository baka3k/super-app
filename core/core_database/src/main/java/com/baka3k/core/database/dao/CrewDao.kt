package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.baka3k.core.database.model.CrewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CrewDao {

    @Query(
        value = """
        SELECT * FROM crew
        WHERE movieId = :movieId
    """
    )
    fun getCrew(movieId: Long): Flow<List<CrewEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCrew(crewEntities: List<CrewEntity>): List<Long>

    @Update
    suspend fun updateCrew(entities: List<CrewEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertCrew(entities: List<CrewEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreCrew,
        updateMany = ::updateCrew
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM crew
            WHERE id in (:ids)
        """
    )
    suspend fun deleteCrew(ids: List<Int>)

    @Query(
        value = """
            SELECT COUNT(*) FROM crew

        """
    )
    suspend fun countCrew(): Int
}