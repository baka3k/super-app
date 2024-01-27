package com.baka3k.core.data.movie.repository

import com.baka3k.core.common.result.Result
import com.baka3k.core.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPerson(personId: String): Flow<Result<Person>>
}