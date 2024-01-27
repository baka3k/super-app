package com.baka3k.core.data.movie.repository

import com.baka3k.core.common.result.Result
import com.baka3k.core.model.Cast
import com.baka3k.core.model.Crew
import kotlinx.coroutines.flow.Flow

interface CreditRepository {
    fun getCastStream(movieId: Long): Flow<List<Cast>>
    fun getCrewStream(movieId: Long): Flow<List<Crew>>
    suspend fun getCredit(movieId: Long): Result<Int>
}