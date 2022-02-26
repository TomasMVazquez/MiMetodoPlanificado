package com.applications.toms.mimetodoplanificado.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction
import com.applications.toms.mimetodoplanificado.ui.AppState
import com.applications.toms.mimetodoplanificado.ui.navigation.NavCommand.*
import com.applications.toms.mimetodoplanificado.ui.screen.aboutus.AboutUs
import com.applications.toms.mimetodoplanificado.ui.screen.home.Home
import com.applications.toms.mimetodoplanificado.ui.screen.home.OnBoarding
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(appState: AppState) {

    val showOnBoarding = appState.showOnBoarding

    NavHost(
        navController = appState.navController,
        startDestination = NavFeature.HOME.route
    ) {
        nav(navController = appState.navController, showOnBoarding){
            appState.setMethodChosen(it)
            appState.showModalSheet()
        }
    }

}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.nav (
    navController: NavController,
    showOnBoarding: Boolean,
    goToSettings: (Method) -> Unit
) {
    navigation(
        startDestination = ContentType(NavFeature.ON_BOARDING).route,
        route = NavFeature.HOME.route
    ){
        composable(navCommand = ContentType(NavFeature.ON_BOARDING)) {
            if (showOnBoarding) {
                OnBoarding(
                    onGettingStartedClick = { onFinishOnBoarding(navController) },
                    onSkipClicked = { onFinishOnBoarding(navController) }
                )
            } else {
                onFinishOnBoarding(navController)
            }
        }

        composable(navCommand = ContentType(NavFeature.HOME)){
            Home { method, userAction ->
                when (userAction) {
                    UserAction.ABOUT_US -> {
                        navController.navigate(ContentType(NavFeature.ABOUT_US).route)
                    }
                    else -> method?.let { goToSettings(it) }
                }
            }
        }

        composable(navCommand = ContentType(NavFeature.ABOUT_US)) {
            AboutUs {
                navController.popBackStack()
            }
        }
    }

}

private fun onFinishOnBoarding(navController: NavController) {
    navController.navigate(
        ContentType(NavFeature.HOME).route
    ) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navCommand.route,
        arguments = navCommand.args
    ){
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}