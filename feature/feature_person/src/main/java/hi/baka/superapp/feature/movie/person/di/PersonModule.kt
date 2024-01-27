package hi.baka.superapp.feature.movie.person.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import hi.baka.superapp.feature.movie.person.PersonViewModel
import hi.baka.superapp.feature.movie.person.interactor.GetPersonUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@InstallIn(FragmentComponent::class)
@Module
class PersonModule{
    @Provides
    fun providePostDetailViewModel(fragment: Fragment, factory: PostDetailViewModelFactory) =
        ViewModelProvider(fragment, factory).get(PersonViewModel::class.java)

    @Provides
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

}
class PostDetailViewModelFactory @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutineScope: CoroutineScope,
    private val getPersonUseCase: GetPersonUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != PersonViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
//        savedStateHandle: SavedStateHandle,
//        getPersonUseCase: GetPersonUseCase,
//        @Dispatcher(HiDispatchers.SERIAL) private val ioDispatcher: CoroutineDispatcher,
        return PersonViewModel(
            savedStateHandle = savedStateHandle,
            getPersonUseCase=getPersonUseCase,
            ioDispatcher = Dispatchers.IO
        ) as T
    }
}