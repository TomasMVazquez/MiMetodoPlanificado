package com.applications.toms.mimetodoplanificado.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.applications.toms.mimetodoplanificado.ui.screen.home.Home
import com.applications.toms.mimetodoplanificado.ui.screen.home.OnBoarding
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController, hasOnBoardingAlreadyShown:Boolean) {

    NavHost(
        navController = navController,
        startDestination = NavFeature.HOME.route
    ) {
        homeNav(navController = navController, !hasOnBoardingAlreadyShown)
    }

}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.homeNav(
    navController: NavController,
    showOnboarding: Boolean
) {
    navigation(
        startDestination = NavCommand.ContentType(NavFeature.HOME).route,
        route = NavFeature.HOME.route
    ) {

        composable(navCommand = NavCommand.ContentType(NavFeature.HOME)) {
            if (showOnboarding) {
                OnBoarding(
                    onGettingStartedClick = {
                        navController.navigate(NavCommand.ContentType(NavFeature.HOME).route)
                    },
                    onSkipClicked = {
                        navController.navigate(NavCommand.ContentType(NavFeature.HOME).route)
                    }
                )
            }
            else {
                Home()
            }
        }

    }
}

/*
@ExperimentalFoundationApi
private fun NavGraphBuilder.typeNav(
    navController: NavController
) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.BY_TYPE).route,
        route = Feature.BY_TYPE.route
    ) {

        composable(navCommand = NavCommand.ContentType(Feature.BY_TYPE)) {
            ByTypeScreen {
                navController.navigate(NavCommand.ContentDetail(Feature.BY_TYPE).createNavRout(it.id))
            }
        }

        composable(navCommand = NavCommand.ContentDetail(Feature.BY_TYPE)) { backStackEntry ->
            PokemonDetail(
                id = backStackEntry.findArg(NavArg.Id)
            )
        }
    }
}*/

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