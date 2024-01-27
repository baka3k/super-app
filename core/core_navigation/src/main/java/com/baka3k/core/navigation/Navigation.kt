package com.baka3k.core.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation

interface Screen {
    val startScreen: String
    val destinationScreen: String

    /**
     * use for deeplink
     * */
    val deepLinkUrl: String
}

interface Router {
    /**
     * Open new [screen]
     */
    fun navigate(screen: Screen)

    /**
     * Open new [screen]
     */
    fun navigate(screen: Screen, data: Map<String, String>, navOptions: NavOptions)

    /**
     * Open new [screen] with [data]
     */
    fun navigate(screen: Screen, data: Map<String, String>)

    /**
     * Back to previous screen.
     *
     * @return Result whether pop successfully
     */
    fun backToPreviousScreen(): Boolean

    /**
     * Back to specific [destinationScreen]
     *
     * @return Result whether pop successfully
     */
    fun backToPreviousScreen(destinationScreen: Screen): Boolean
}

fun NavController.navigateScreen(screen: Screen) {
    navigateScreen(screen, mapOf(), null)
}

fun NavController.navigateScreen(screen: Screen, data: Map<String, String>) {
    navigateScreen(screen, data, null)
}

fun NavController.navigateScreen(screen: Screen, params: Map<String, String>, navOptions: NavOptions?) {
    navigate(screen.deepLinkUrl.toUriWithParams(params = params), navOptions)
}

fun NavController.backToPreviousScreen(): Boolean {
    return popBackStack()
}

fun NavController.backToPreviousScreen(destinationScreen: Screen): Boolean {
    return popBackStack(
        route = destinationScreen.startScreen,
        inclusive = false
    )
}

inline fun String.toUriWithParams(params: Map<String, String>?): Uri {
    var uri = "$this"
    if (params != null && params.isNotEmpty()) {
        uri += "?"
    }
    params?.forEach { (key, value) ->
        uri += "$key=$value&"
    }
    return uri.toUri()
}