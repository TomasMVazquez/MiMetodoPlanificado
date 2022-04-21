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
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.cancelRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.cancelRepeatingNotification
import com.applications.toms.mimetodoplanificado.ui.navigation.NavCommand.ContentType
import com.applications.toms.mimetodoplanificado.ui.screen.aboutus.AboutUs
import com.applications.toms.mimetodoplanificado.ui.screen.alarmsettings.AlarmSettings
import com.applications.toms.mimetodoplanificado.ui.screen.home.Home
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethod
import com.applications.toms.mimetodoplanificado.ui.screen.onboarding.OnBoarding
import com.applications.toms.mimetodoplanificado.ui.utils.onMethodHasBeenSaved
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(
    navController: NavHostController,
    shouldShowOnBoarding: Boolean,
    isMethodSaved: Boolean,
    onFinishOnBoarding: () -> Unit,
    goToSettings: (Method) -> Unit,
    onMethodChanged: () -> Unit
) {
    if (shouldShowOnBoarding){
        NavHost(
            navController = navController,
            startDestination = NavFeature.ON_BOARDING.route
        ) {
            onBoardingNav(onFinishOnBoarding)
        }
    } else {
        NavHost(
            navController = navController,
            startDestination = NavFeature.HOME.route
        ) {
            nav(
                navController = navController,
                isMethodSaved = isMethodSaved,
                goToSettings = goToSettings,
                onMethodChanged = onMethodChanged
            )
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

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.nav (
    navController: NavController,
    isMethodSaved: Boolean,
    goToSettings: (Method) -> Unit,
    onMethodChanged: () -> Unit
) {
    if (isMethodSaved) {
        navigation(
            startDestination = ContentType(NavFeature.MY_METHOD).route,
            route = NavFeature.MY_METHOD.route
        ){
            composable(navCommand = ContentType(NavFeature.MY_METHOD)) {
                MyMethod(
                    onMethodDeleted = { wasNotificationEnable, wasAlarmEnable ->
                        onMethodHasBeenSaved(navController.context,false)
                        if (wasNotificationEnable) cancelRepeatingNotification(navController.context)
                        if (wasAlarmEnable) cancelRepeatingAlarm(navController.context)
                        onMethodChanged()
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
    } else {
        navigation(
            startDestination = ContentType(NavFeature.HOME).route,
            route = NavFeature.HOME.route
        ){
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