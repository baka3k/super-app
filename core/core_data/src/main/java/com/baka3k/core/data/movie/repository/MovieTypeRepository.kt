package com.baka3k.core.data.movie.repository

import com.baka3k.core.data.Syncable

interface MovieTypeRepository:Syncable  {
    suspend fun initDefaultType()
}