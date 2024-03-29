package hi.baka.feature.compose.person.ui

import android.content.Context
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import com.baka3k.core.model.Person

class PersonView(context: Context) : FrameLayout(context) {
    private var composeView: ComposeView = ComposeView(context)
    init {
        addView(composeView)
    }

    fun loadData(person: Person) {
        composeView.setContent {
            PersonScreen(person = person)
        }
    }
}