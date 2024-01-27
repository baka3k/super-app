package com.baka3k.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
//import com.baka3k.core.common.Dispatcher
//import com.baka3k.core.common.HiDispatchers
import com.baka3k.core.datastore.IntToStringIdsMigration
import com.baka3k.core.datastore.UserPreferences
import com.baka3k.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(HiDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
            migrations = listOf(
                IntToStringIdsMigration,
            )
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}
