package com.applications.toms.mimetodoplanificado.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.cancelRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.cancelRepeatingNotification
import com.applications.toms.mimetodoplanificado.ui.navigation.NavCommand.ContentType
import com.applications.toms.mimetodoplanificado.ui.screen.aboutus.AboutUs
import com.applications.toms.mimetodoplanificado.ui.screen.alarmsettings.AlarmSettings
import com.applications.toms.mimetodoplanificado.ui.screen.home.Home
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodScreen
import com.applications.toms.mimetodoplanificado.ui.screen.onboarding.OnBoarding
import com.applications.toms.mimetodoplanificado.ui.utils.onSavedMethod
import com.google.accompanist.pager.ExperimentalPagerApi

enum class NavigationState {
    ON_BOARDING,
    HOME,
    MY_METHOD
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(
    navController: NavHostController,
    navigationState: NavigationState,
    onChangeNavigationState: (NavigationState) -> Unit
) {
    when(navigationState){
        NavigationState.ON_BOARDING -> {
            NavHost(
                navController = navController,
                startDestination = NavFeature.ON_BOARDING.route
            ) {
                onBoardingNav{
                    onChangeNavigationState(NavigationState.HOME)
                }
            }
        }
        NavigationState.HOME -> {
            NavHost(
                navController = navController,
                startDestination = NavFeature.HOME.route
            ) {
                homeNav(
                    navController = navController
                ) {
                    onChangeNavigationState(NavigationState.MY_METHOD)
                }
            }
        }
        NavigationState.MY_METHOD -> {
            NavHost(
                navController = navController,
                startDestination = NavFeature.MY_METHOD.route
            ) {
                myMethodNav(
                    navController = navController
                ) {
                    onChangeNavigationState(NavigationState.HOME)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.onBoardingNav (
    onFinishOnBoarding: () -> Unit
) {
    navigation(
        startDestination = ContentType(NavFeature.ON_BOARDING).route,
        route = NavFeature.ON_BOARDING.route
    ){
        composable(navCommand = ContentType(NavFeature.ON_BOARDING)) {
            OnBoarding(
                onGettingStartedClick = { onFinishOnBoarding() },
                onSkipClicked = { onFinishOnBoarding() }
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
private fun NavGraphBuilder.myMethodNav (
    navController: NavController,
    onMethodDeleted: () -> Unit
) {
    navigation(
        startDestination = ContentType(NavFeature.MY_METHOD).route,
        route = NavFeature.MY_METHOD.route
    ){
        composable(navCommand = ContentType(NavFeature.MY_METHOD)) {
            MyMethodScreen(
                onMethodDeleted = { wasNotificationEnable, wasAlarmEnable ->
                    onSavedMethod(navController.context,false)
                    if (wasNotificationEnable) cancelRepeatingNotification(navController.context)
                    if (wasAlarmEnable) cancelRepeatingAlarm(navController.context)
                    onMethodDeleted()
                },
                goToAlarmSettings = {
                    navController.navigate(ContentType(NavFeature.ALARM_SETTINGS).route)
                }
            )
        }

        composable(navCommand = ContentType(NavFeature.ALARM_SETTINGS)){
            AlarmSettings(
                onSave = {
                    navController.navigate(ContentType(NavFeature.MY_METHOD).route)
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.homeNav (
    navController: NavController,
    onMethodSaved: () -> Unit
) {

    navigation(
        startDestination = ContentType(NavFeature.HOME).route,
        route = NavFeature.HOME.route
    ){
        composable(navCommand = ContentType(NavFeature.HOME)){
            Home(
                goToAboutUs = {
                    navController.navigate(ContentType(NavFeature.ABOUT_US).route)
                },
                goToMyMethod = onMethodSaved
            )
        }

        composable(navCommand = ContentType(NavFeature.ABOUT_US)) {
            AboutUs {
                navController.popBackStack()
            }
        }
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