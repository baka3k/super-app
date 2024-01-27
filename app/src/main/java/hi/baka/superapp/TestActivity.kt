package hi.baka.superapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

import dagger.hilt.android.AndroidEntryPoint
import hi.baka.superapp.ui.HiComposeApp


@AndroidEntryPoint
class TestActivity : BaseSplitActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this@TestActivity)
            HiComposeApp(windowSizeClass = windowSizeClass)
        }
    }
}