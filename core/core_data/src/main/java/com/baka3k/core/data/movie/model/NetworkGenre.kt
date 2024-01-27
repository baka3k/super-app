package com.baka3k.core.data.movie.model

import com.baka3k.core.database.model.GenreEntity
import com.baka3k.core.database.model.MovieType
import com.baka3k.core.network.model.NetworkGenre


fun NetworkGenre.asEntity() = GenreEntity(
    id = id,
    name = name
)