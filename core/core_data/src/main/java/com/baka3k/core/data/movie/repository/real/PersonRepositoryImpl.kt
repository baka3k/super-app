package com.baka3k.core.data.movie.repository.real

import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import com.baka3k.core.common.result.Result
import com.baka3k.core.data.movie.model.asEntity
import com.baka3k.core.data.movie.repository.PersonRepository
import com.baka3k.core.database.dao.PersonDao
import com.baka3k.core.database.model.asExternalModel
//import com.baka3k.core.datastore.HiPreferencesDataSource
import com.baka3k.core.model.Person
import com.baka3k.core.network.datasource.PersonNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val personDao: PersonDao,
    private val network: PersonNetworkDataSource,
//    private val preference: HiPreferencesDataSource,
    @Dispatcher(HiDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : PersonRepository {

    override fun getPerson(personId: String): Flow<Result<Person>> = flow {
        when (val response = network.getPerson(personId)) {
            is Result.Success -> {
                val data = response.data
                personDao.insertOrIgnorePerson(
                    listOf(
                        data.asEntity()
                    )
                )
                val person = personDao.getPerson(personId)
                val result = Result.Success(person.asExternalModel())
                emit(result)
            }
            is Result.Error -> {
                emit(Result.Error(response.exception))
            }
            else -> {
                emit(Result.Error(Exception("Unknow Error")))
            }
        }
    }.flowOn(ioDispatcher)

    companion object {
        private const val TAG = "PersonRepositoryImpl"
    }
}