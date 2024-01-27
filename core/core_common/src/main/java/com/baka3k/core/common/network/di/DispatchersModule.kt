package com.baka3k.core.common.network.di

import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(HiDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(HiDispatchers.SERIAL)
    fun providesSerialDispatcher(): CoroutineDispatcher = Dispatchers.IO.limitedParallelism(1)
//        newSingleThreadContext("HiThread")
//        Executors.newSingleThreadExecutor().asCoroutineDispatcher()
}
//@OptIn(DelicateCoroutinesApi::class)
//private val backgroundDispatcher = newFixedThreadPoolContext(4, "App background")
//private val imageProcessDispatcher = backgroundDispatcher.limitedParallelism(2)
//private val imagejsonProcessingDispatcher = backgroundDispatcher.limitedParallelism(2)
//private val fileWriterProcessingDispatcher = backgroundDispatcher.limitedParallelism(1)

