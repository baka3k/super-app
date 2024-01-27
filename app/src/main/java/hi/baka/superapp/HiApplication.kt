package hi.baka.superapp

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompatApplication
import dagger.hilt.android.HiltAndroidApp
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import com.google.android.play.core.splitcompat.SplitCompat

@HiltAndroidApp
class HiApplication: SplitCompatApplication(),ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }
    override fun onCreate() {
        super.onCreate()
//        Sync.initialize(context = this)
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}