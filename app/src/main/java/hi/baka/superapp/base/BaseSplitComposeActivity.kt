package hi.baka.superapp.base

import android.content.Context
import androidx.activity.ComponentActivity
import com.google.android.play.core.splitcompat.SplitCompat

open class BaseSplitComposeActivity: ComponentActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
//        SplitCompat.install(this)
        SplitCompat.installActivity(this)
    }
}