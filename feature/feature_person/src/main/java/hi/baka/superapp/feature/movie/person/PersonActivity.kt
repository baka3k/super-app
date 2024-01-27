package hi.baka.superapp.feature.movie.person

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import hi.baka.superapp.BaseSplitActivity

class PersonActivity : BaseSplitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("person module")
            PersonScreenRouter{

            }
        }
    }
}