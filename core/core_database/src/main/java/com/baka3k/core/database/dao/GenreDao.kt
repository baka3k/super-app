package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baka3k.core.database.model.GenreEntity
import com.baka3k.core.database.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreGenre(typeEntities: List<GenreEntity>): List<Long>
    @Query(
        value = """
        SELECT * FROM genre 
        WHERE id = :id
    """
    )
    fun getGenreEntity(id: String): Flow<GenreEntity>
    @Query(
        value = """
            SELECT COUNT(*) FROM genre

        """
    )
    suspend fun getCount(): Int
}