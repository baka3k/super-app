package hi.baka.feature.react


import hi.baka.superapp.BaseSplitActivity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.FrameLayout
import com.baka3k.core.common.result.Result
import com.baka3k.core.data.di.DataModule
import com.baka3k.core.data.movie.model.PhotoSize
import com.baka3k.core.data.movie.model.asPhotoUrl
import com.baka3k.core.data.movie.repository.PersonRepository
import com.baka3k.core.data.movie.repository.real.MovieRepositoryImpl
import com.baka3k.core.data.movie.repository.real.PersonRepositoryImpl
import com.baka3k.core.database.DaosModule
import com.baka3k.core.database.DatabaseModule
import com.baka3k.core.network.datasource.retrofit.movie.RetrofitMovieNetworkDataSource
import com.baka3k.core.network.datasource.secure.SSlContextVerification
import com.baka3k.core.network.di.NetworkModule
import com.facebook.hermes.reactexecutor.HermesExecutorFactory
import com.facebook.react.BuildConfig
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.soloader.SoLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReactActivity : BaseSplitActivity(), DefaultHardwareBackBtnHandler {
    private var mReactRootView: ReactRootView? = null
    private val OVERLAY_PERMISSION_REQ_CODE = 1
    private lateinit var btnCloseActivity: Button
    private lateinit var mReactInstanceManager: ReactInstanceManager
    private var personID = ""
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPersonId()
        setContentView(R.layout.activity_react)
        btnCloseActivity = findViewById(R.id.btnCloseActivity)
        checkAndAskForOverlayPermission()
        initReactInstanceManager()
        btnCloseActivity.setOnClickListener {
            finish()
        }
        test()
    }

    fun test() {
        val database = DatabaseModule.providesHiDatabase(applicationContext)
        val movieDao = DaosModule.providesMovieDao(database)
        val genreDao = DaosModule.providesGenreDao(database)
        val personDao = DaosModule.providesPersonDao(database)
        val movieGenreDao = DaosModule.providesMovieGenreDao(database)
        val movieTypeDao = DaosModule.providesMovieTypeDao(database)
        val networkDataSource = RetrofitMovieNetworkDataSource(
            networkJson = NetworkModule.providesNetworkJson(),
            sslContextVerification = SSlContextVerification(
                assetManager = applicationContext.assets
            )
        )
        val movieRepository = MovieRepositoryImpl(
            movieDao = movieDao,
            movieTypeDao = movieTypeDao,
            movieGenreDao = movieGenreDao,
            ioDispatcher = Dispatchers.IO,
            network = networkDataSource,
        )
        val personRepository = PersonRepositoryImpl(
            personDao = personDao,
            network = networkDataSource,
            ioDispatcher = Dispatchers.IO,
        )
        coroutineScope.launch {
            personRepository.getPerson(personID).collect {
                Log.d("ReactActivity", "Person: $it")
                if (it is Result.Success) {
                    val person = it.data
                    val imageURL = person.profilePath.asPhotoUrl(PhotoSize.Profile.h632)
                    val name = person.name
                    val alsoKnowAs = person.alsoKnownAs
                    val birthday = person.birthday
                    val deathday = person.deathday
                    val gender = person.gender
                    val placeOfBirth = person.placeOfBirth
                    val biography = person.biography
                    val homepage = person.homepage
                    Log.d(TAG, "#loadPerson() imageURL: $imageURL")
                    withContext(Dispatchers.Main)
                    {
                        loadReactNativeApp(imageURL)
                    }
                }
                else if(it is Result.Error){
                    val error = it.exception
                    Log.d(TAG, "#loadPerson(): ${it.exception}")
                }
            }
        }
    }

    private fun initPersonId() {
        val intent = intent
        personID = intent.data?.getQueryParameter("screenId") ?: ""
        Log.d(TAG, "personId: $personID")
    }

    private fun checkAndAskForOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
            }
        }
    }

    //
    override fun onPause() {
        mReactInstanceManager.onHostPause(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mReactInstanceManager.onHostResume(this, this)
    }

    override fun onDestroy() {
        mReactInstanceManager.onHostDestroy(this)
        mReactRootView?.unmountReactApplication()
        super.onDestroy()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun loadReactNativeApp(imageURL: String) {

        mReactRootView = ReactRootView(this)
        mReactRootView?.startReactApplication(
            mReactInstanceManager,
            "baka3k",
            buildInitProperties(imageURL)
        )
        val contentView = findViewById<FrameLayout>(R.id.contentView)
        contentView.removeAllViews()
        contentView.addView(mReactRootView)
    }

    private fun initReactInstanceManager() {
        SoLoader.init(applicationContext, false)
        val packages: List<ReactPackage> = PackageList(application).packages
        mReactInstanceManager = ReactInstanceManager.builder()
            .setJavaScriptExecutorFactory(HermesExecutorFactory())
            .setApplication(application)
            .setCurrentActivity(this)
            .setBundleAssetName("index.android.bundle")
            .setJSBundleFile("assets://index.android.bundle")
            .setJSMainModulePath("index")
            .addPackages(packages)
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build()
    }

    private fun buildInitProperties(imageURL: String): Bundle {
//        val imageURL = "https://image.tmdb.org/t/p/h632/x0NqVqpJSET0oVdJxbbwwd7hrFA.jpg"
        val initialProperties = Bundle().apply { putString("url", imageURL) }
        return initialProperties

    }

    override fun invokeDefaultOnBackPressed() {
        finish()
    }

    companion object {
        const val TAG = "ReactActivity"
    }
}