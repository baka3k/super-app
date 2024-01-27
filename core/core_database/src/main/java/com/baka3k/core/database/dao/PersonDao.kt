package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.baka3k.core.database.model.PersonEntity

@Dao
interface PersonDao {

    @Query(
        value = """
        SELECT * FROM `person`
        WHERE id = :personId
    """
    )
    fun getPerson(personId: String): PersonEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePerson(entities: List<PersonEntity>): List<Long>

    @Update
    suspend fun updatePerson(entities: List<PersonEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertPerson(entities: List<PersonEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnorePerson,
        updateMany = ::updatePerson
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM `person`
            WHERE id in (:ids)
        """
    )
    suspend fun deletePerson(ids: List<String>)

    @Query(
        value = """
            SELECT COUNT(*) FROM `PERSON`

        """
    )
    suspend fun count(): Int
}