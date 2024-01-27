package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baka3k.core.database.model.TypeEntity

@Dao
interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreMovieType(typeEntities: List<TypeEntity>): List<Long>
    @Query(
        value = """
            SELECT COUNT(*) FROM TYPE

        """
    )
    suspend fun getCount(): Int
}