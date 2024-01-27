package hi.baka.superapp.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

class PersonModuleDependency {
}
@EntryPoint
@InstallIn(SingletonComponent::class)
interface LoginModuleDependencies {

}