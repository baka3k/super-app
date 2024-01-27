package com.baka3k.core.data.movie.repository.real

import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import com.baka3k.core.data.Synchronizer
import com.baka3k.core.data.movie.model.asEntity
import com.baka3k.core.data.movie.repository.GenreRepository
import com.baka3k.core.database.dao.GenreDao
import com.baka3k.core.database.dao.MovieGenreDao
import com.baka3k.core.database.model.asExternalModel
import com.baka3k.core.model.Genre
import com.baka3k.core.network.datasource.GenreNetworkDataSource
import com.baka3k.core.network.model.NetworkGenre
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreDao: GenreDao,
    private val movieGenreDao: MovieGenreDao,
    private val genreNetworkDataSource: GenreNetworkDataSource,
    @Dispatcher(HiDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GenreRepository {
    override fun getGenres(movieId: Long): Flow<List<Genre>> =
        movieGenreDao.getGenreEntitiesStream(movieId).map { genreEntities ->
            genreEntities.map { genre ->
                genre.asExternalModel()
            }
        }.flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = withContext(ioDispatcher) {
        val movieGenre = async {
            when (val networkGenresResult = genreNetworkDataSource.getGenresMovie()) {
                is com.baka3k.core.common.result.Result.Success -> {
                    val genres = networkGenresResult.data
                    genreDao.insertOrIgnoreGenre(genres.map(NetworkGenre::asEntity))
                    true
                }
                else -> {
                    false
                }
            }
        }
        val tvGenre = async {
            when (val networkGenresResult = genreNetworkDataSource.getGenreTvList()) {
                is com.baka3k.core.common.result.Result.Success -> {
                    val genres = networkGenresResult.data
                    genreDao.insertOrIgnoreGenre(genres.map(NetworkGenre::asEntity))
                    true
                }
                else -> {
                    false
                }
            }
        }
        movieGenre.await() && tvGenre.await()
    }
}