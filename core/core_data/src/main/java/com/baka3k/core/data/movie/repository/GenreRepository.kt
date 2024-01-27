package com.baka3k.core.data.movie.repository

import com.baka3k.core.data.Syncable
import com.baka3k.core.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository:Syncable  {
     fun getGenres(movieId: Long): Flow<List<Genre>>
}