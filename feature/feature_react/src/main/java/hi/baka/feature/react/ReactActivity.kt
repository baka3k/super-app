package hi.baka.feature.react


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.compose.ui.platform.ComposeView
import com.baka3k.core.common.result.Result
import com.baka3k.core.data.movie.model.PhotoSize
import com.baka3k.core.data.movie.model.asPhotoUrl
import com.baka3k.core.data.movie.repository.real.MovieRepositoryImpl
import com.baka3k.core.data.movie.repository.real.PersonRepositoryImpl
import com.baka3k.core.database.DaosModule
import com.baka3k.core.database.DatabaseModule
import com.baka3k.core.model.Person
import com.baka3k.core.network.datasource.retrofit.movie.RetrofitMovieNetworkDataSource
import com.baka3k.core.network.datasource.secure.SSlContextVerification
import com.baka3k.core.network.di.NetworkModule
import com.facebook.hermes.reactexecutor.HermesExecutorFactory
import com.facebook.react.BuildConfig
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceEventListener
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.soloader.SoLoader
import hi.baka.feature.compose.person.interactor.GetPersonUseCase
import hi.baka.feature.compose.person.ui.PersonScreen
import hi.baka.feature.react.bridge.HiAppReactPackage
import hi.baka.feature.react.bundle.JsBundleFileManager
import hi.baka.feature.react.bundle.JsBundleLoader
import hi.baka.feature.react.security.JsBundleVerification
import hi.baka.superapp.BaseSplitActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class ReactActivity : BaseSplitActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val reactInstanceEventListener = ReactInstanceEventListener { }
    private val buttonOnClicked = OnClickListener {
        handleButtonOnClicked(it)
    }
    private lateinit var getPersonUseCase: GetPersonUseCase
    private lateinit var progressBar: ProgressBar
    private lateinit var btnLoadComposeView: Button
    private lateinit var btnLoadJsBundle1: Button
    private lateinit var btnLoadJsBundle2: Button
    private lateinit var btnLoadJsBundle3: Button
    private lateinit var contentView: FrameLayout
    private lateinit var composeView: ComposeView
    private lateinit var mReactInstanceManager: ReactInstanceManager
    private lateinit var jsBundleFileManager: JsBundleFileManager
    private lateinit var jsBundleLoader: JsBundleLoader
    private var mReactRootView: ReactRootView? = null
    private var person: Person? = null
    private var personID = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_react)
        initPersonId()
        initReactNative()
        initViews()
        checkAndAskForOverlayPermission()
        initReactInstanceManager()
        initDataProvider()
        loadData()

        initJsBundleForTestLoadLocal()
    }

    private fun initViews() {
        composeView = ComposeView(this)
        contentView = findViewById(R.id.contentView)
        progressBar = findViewById(R.id.progressBar)
        btnLoadComposeView = findViewById(R.id.btnLoadComposeView)
        btnLoadJsBundle1 = findViewById(R.id.btnLoadJsBundle1)
        btnLoadJsBundle2 = findViewById(R.id.btnLoadJsBundle2)
        btnLoadJsBundle3 = findViewById(R.id.btnLoadJsBundle3)
        mReactRootView = ReactRootView(this)
        btnLoadComposeView.setOnClickListener(buttonOnClicked)
        btnLoadJsBundle1.setOnClickListener(buttonOnClicked)
        btnLoadJsBundle2.setOnClickListener(buttonOnClicked)
        btnLoadJsBundle3.setOnClickListener(buttonOnClicked)
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
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
            }
        }
    }

    private fun initDataProvider() {
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
        getPersonUseCase = GetPersonUseCase(
            personRepository = personRepository
        )
    }

    private fun showLoading() {
        if (progressBar.visibility == View.GONE) {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        if (progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.GONE
        }
    }

    private fun loadData() {
        showLoading()
        coroutineScope.launch {
            getPersonUseCase.invoke(personID).collect {
                Log.d("ReactActivity", "Person: $it")
                if (it is Result.Success) {
                    person = it.data
                    withContext(Dispatchers.Main) {
                        hideLoading()
                        loadComposeView()// default
                    }
                } else if (it is Result.Error) {
                    Log.d(TAG, "#loadPerson(): ${it.exception}")
                }
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
        mReactInstanceManager.onHostResume(this)
    }

    override fun onDestroy() {
        contentView.removeAllViews()
        removeReactInstanceManager()
        mReactRootView?.unmountReactApplication()
        coroutineScope.cancel()
        Log.d(TAG, "#onDestroy()")
        super.onDestroy()
    }

    private fun removeReactInstanceManager() {
        mReactInstanceManager.removeReactInstanceEventListener(reactInstanceEventListener)
        mReactInstanceManager.onHostDestroy(this)
        mReactInstanceManager.destroy()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            mReactInstanceManager.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun initReactNative() {
        SoLoader.init(applicationContext, false)
        jsBundleFileManager = JsBundleFileManager(rootFolder = filesDir)
        jsBundleLoader = JsBundleLoader(
            jsBundleFileManager = jsBundleFileManager,
            jsBundleVerification = JsBundleVerification()
        )
    }

    private fun initReactInstanceManager(
        bundleAssetName: String = "index.android.bundle",
        jsBundleFile: String = "assets://index.android.bundle",
        jsMainModulePath: String = "index"
    ) {
        val packages: ArrayList<ReactPackage> = PackageList(application).packages
        packages.add(HiAppReactPackage())
        mReactInstanceManager =
            ReactInstanceManager.builder().setJavaScriptExecutorFactory(HermesExecutorFactory())
                .setApplication(application).setCurrentActivity(this)
                .setBundleAssetName(bundleAssetName)
                .setJSBundleFile(jsBundleFile) // for test - load in assets
                .setJSMainModulePath(jsMainModulePath).addPackages(packages)
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED).build()
        mReactInstanceManager.addReactInstanceEventListener(reactInstanceEventListener)
    }

    @Throws(SecurityException::class, java.io.IOException::class)
    private fun verifyJsBundle() {
        try {
            val jsBundleFile = "assets://index.android.bundle" // for test - load in assets
            val jsBundleVerification = JsBundleVerification()
            val inputStream = assets.open(jsBundleFile)
            jsBundleVerification.verifyJsBundle("person", inputStream)
            inputStream.close()
        } catch (exception: SecurityException) {
            throw exception
        } catch (exception: IOException) {
            throw exception
        }
    }

    private fun buildInitProperties(person: Person): Bundle {
        val imageURL = person.profilePath.asPhotoUrl(PhotoSize.Profile.h632)
        val name = person.name
        val alsoKnowAs = person.alsoKnownAs
        val birthday = person.birthday
        val deathday = person.deathday
        val gender = person.gender
        val placeOfBirth = person.placeOfBirth
        val biography = person.biography
        val homepage = person.homepage
        val knownForDepartment = person.knownForDepartment
        Log.d(TAG, "#loadPerson() imageURL: $imageURL")
        val initialProperties = Bundle()
        initialProperties.putString("url", imageURL)
        initialProperties.putString("name", name)
        initialProperties.putString("homepage", homepage)
        initialProperties.putStringArrayList("alsoKnowAs", ArrayList(alsoKnowAs))
        initialProperties.putString("dateofbirth", birthday)
        initialProperties.putString("deathday", deathday)
        initialProperties.putString("knownfordepartment", knownForDepartment)
        initialProperties.putString("biography", biography)

        return initialProperties

    }

    private fun btnLoadJsBundle1() {
        if (person != null) {
            removeReactInstanceManager()
            initReactInstanceManager()
            mReactRootView?.unmountReactApplication()
            mReactRootView?.startReactApplication(
                mReactInstanceManager, "baka3k", buildInitProperties(person!!)
            )
            contentView.removeAllViews()
            contentView.addView(mReactRootView)
        } else {
            Log.w(TAG, "#btnLoadJsBundle1() person null")
        }
    }

    private fun btnLoadJsBundle2() {
        if (person != null) {
            showLoading()
            coroutineScope.launch {
//                val miniAppId = "person2"
//                val jsBundleFileName = "index1.android.bundle"
                val miniAppId = "person4"
                val jsBundleFileName = "index4.android.bundle"
                val result = jsBundleLoader.loadJsBundleFile(
                    miniAppId = miniAppId,
                    jsBundleFileName = jsBundleFileName
                )
                if (result.isSuccess) {
                    val jsBundleFile = result.getOrNull()
                    if (jsBundleFile != null) {
                        withContext(Dispatchers.Main) {
                            removeReactInstanceManager()
                            initReactInstanceManager(
                                jsBundleFile = jsBundleFile.jsBundleFile,
                                bundleAssetName = jsBundleFile.bundleAssetName,
                                jsMainModulePath = jsBundleFile.jsMainModulePath
                            )
                            mReactRootView?.unmountReactApplication()
                            mReactRootView?.startReactApplication(
                                mReactInstanceManager, "baka3k", buildInitProperties(person!!)
                            )
                            contentView.removeAllViews()
                            contentView.addView(mReactRootView)
                            hideLoading()
                        }
                    }
                } else {
                    Log.w(TAG, "#btnLoadJsBundle2() result: ${result.exceptionOrNull()}")
                }
            }
        } else {
            Log.w(TAG, "#btnLoadJsBundle2() person null")
        }
    }
    private fun btnLoadJsBundle3() {
        if (person != null) {
            showLoading()
            coroutineScope.launch {
                val miniAppId = "person3"
                val result = jsBundleLoader.loadJsBundleFile(
                    miniAppId = miniAppId,
                    jsBundleFileName = "index2.android.bundle"
                )
                Log.w(TAG, "#btnLoadJsBundle3() result: ${result}")
                if (result.isSuccess) {
                    val jsBundleFile = result.getOrNull()
                    Log.w(TAG, "#btnLoadJsBundle3() jsBundleFile ${jsBundleFile}")
                    if (jsBundleFile != null) {
                        withContext(Dispatchers.Main) {
                            removeReactInstanceManager()
                            initReactInstanceManager(
                                jsBundleFile = jsBundleFile.jsBundleFile,
                                bundleAssetName = jsBundleFile.bundleAssetName,
                                jsMainModulePath = jsBundleFile.jsMainModulePath
                            )
                            mReactRootView?.unmountReactApplication()
                            mReactRootView?.startReactApplication(
                                mReactInstanceManager, "baka3k", buildInitProperties(person!!)
                            )
                            contentView.removeAllViews()
                            contentView.addView(mReactRootView)
                            hideLoading()
                        }
                    }
                } else {
                    Log.w(TAG, "#btnLoadJsBundle3() result: ${result.exceptionOrNull()}")
                }
            }
        } else {
            Log.w(TAG, "#btnLoadJsBundle2() person null")
        }
    }
    private fun loadComposeView() {
        if (person != null) {
            contentView.removeAllViews()
            contentView.addView(composeView)
            composeView.setContent {
                PersonScreen(person!!)
            }
            Log.w(TAG, "#loadComposeView()")
        } else {
            Log.w(TAG, "#loadComposeView() person null")
        }
    }

    private fun handleButtonOnClicked(view: View) {
        if (view.id == R.id.btnLoadComposeView) {
            loadComposeView()
        } else if (view.id == R.id.btnLoadJsBundle1) {
            btnLoadJsBundle1()
        } else if (view.id == R.id.btnLoadJsBundle2) {
            btnLoadJsBundle2()
        }
        else if (view.id == R.id.btnLoadJsBundle3) {
            btnLoadJsBundle3()
        }
    }

    /**
     * Copy file from Asset to local file
     * for testing only
     * will be removed
     * */
    private fun initJsBundleForTestLoadLocal() {
        val jsBundleNames = assets.list("jsbundle")
        if (!jsBundleNames.isNullOrEmpty()) {
            val jsBundleFolderLocal = jsBundleFileManager.getJsBundleFolder()
            jsBundleNames.forEach { assetsName ->
                val inputStream: InputStream
                val out: OutputStream
                try {
                    inputStream = assets.open("jsbundle/$assetsName")
                    val outFile = File(jsBundleFolderLocal, assetsName)
                    out = FileOutputStream(outFile)
                    copyFile(inputStream, out)
                    inputStream.close()
                    out.flush()
                    out.close()
                } catch (e: IOException) {
                    Log.e(
                        TAG,
                        "#initJsBundleForTestLoadLocal() Failed to copy asset file: $assetsName",
                        e
                    )
                }
            }
        } else {
            Log.w(TAG, "#initJsBundleForTestLoadLocal() jsBundleNames empty or null")
        }
    }

    @Throws(java.io.IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while ((inputStream.read(buffer).also { read = it }) != -1) {
            outputStream.write(buffer, 0, read)
        }
    }

    companion object {
        private const val TAG = "ReactActivity"
        private const val OVERLAY_PERMISSION_REQ_CODE = 1
    }
}