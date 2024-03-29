package hi.baka.feature.react.bridge

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewProps
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.views.image.ImageResizeMode
import com.facebook.react.views.image.ReactImageView

class ReactImageManager(
    private val callerContext: ReactApplicationContext
) : SimpleViewManager<ReactImageView>() {

    override fun getName() = REACT_CLASS
    override fun createViewInstance(context: ThemedReactContext): ReactImageView {
        return ReactImageView(context, Fresco.newDraweeControllerBuilder(), null, callerContext)
    }
    @ReactProp(name = "src")
    fun setSrc(view: ReactImageView, sources: ReadableArray?) {
        view.setSource(sources)
    }

    @ReactProp(name = "borderRadius", defaultFloat = 0f)
    override fun setBorderRadius(view: ReactImageView, borderRadius: Float) {
        view.setBorderRadius(borderRadius)
    }

    @ReactProp(name = ViewProps.RESIZE_MODE)
    fun setResizeMode(view: ReactImageView, resizeMode: String?) {
        view.setScaleType(ImageResizeMode.toScaleType(resizeMode))
    }
    companion object {
        const val REACT_CLASS = "RCTImageView"
    }
}