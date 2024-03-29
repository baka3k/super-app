package hi.baka.feature.react.bridge

import android.view.View
import com.baka3k.core.model.Person
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import hi.baka.feature.compose.person.ui.PersonView

class PersonViewManager(
    private val callerContext: ReactApplicationContext
) : SimpleViewManager<View>() {

    override fun getName() = REACT_CLASS
    override fun createViewInstance(context: ThemedReactContext): View {
        // just for test inject native view(compose view) to react native
        val personView = PersonView(context)
        val person = Person(
            profilePath = "https://image.tmdb.org/t/p/h632/abEhV2v27waWWOVt328tD5o9P0C.jpg",
            name = name,
            homepage = "homepage",
            alsoKnownAs = emptyList(),
            deathday = "deathday",
            birthday = "dateofbirth",
            knownForDepartment = "knownfordepartment",
            biography = "biography"
        )
        personView.loadData(person)
        return personView
//        return PersonView(context)
    }

    @ReactProp(name = "loadData")
    fun loadData(
        view: PersonView,
        url: String,
    ) {
        val person = Person(
            profilePath = url,
            name = name,
            homepage = "homepage",
            alsoKnownAs = emptyList(),
            deathday = "deathday",
            birthday = "dateofbirth",
            knownForDepartment = "knownfordepartment",
            biography = "biography"
        )
        view.loadData(person)
    }

    companion object {
        const val REACT_CLASS = "RCTPersonView"
    }
}
