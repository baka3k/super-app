package com.baka3k.core.data.movie.repository.real

import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import com.baka3k.core.data.Synchronizer
import com.baka3k.core.data.movie.repository.MovieTypeRepository
import com.baka3k.core.database.dao.TypeDao
import com.baka3k.core.database.model.MovieType
import com.baka3k.core.database.model.TypeEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieTypeRepositoryImpl @Inject constructor(
    private val movieTypeDao: TypeDao,
    @Dispatcher(HiDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : MovieTypeRepository {
    override suspend fun initDefaultType() {
        withContext(ioDispatcher) {
            val types = mutableListOf<TypeEntity>()
            types.add(TypeEntity(id = MovieType.POPULAR, "POPULAR"))
            types.add(TypeEntity(id = MovieType.NOW_PLAYING, "NOW_PLAYING"))
            types.add(TypeEntity(id = MovieType.TOP_RATE, "TOP_RATE"))
            types.add(TypeEntity(id = MovieType.UP_COMMING, "UP_COMMING"))
            movieTypeDao.insertOrIgnoreMovieType(types)
        }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        initDefaultType()
        return true
    }
}
