package com.applications.toms.mimetodoplanificado.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.applications.toms.mimetodoplanificado.R

enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector,
    @StringRes val title: Int
) {
    HOME(NavCommand.ContentType(NavFeature.HOME), Icons.Default.Home, R.string.home)
}

sealed class NavCommand (
    internal val feature: NavFeature,
    internal val subRoute: String,
    private val navArgs: List<NavArg> = emptyList()
) {

    class ContentType(feature: NavFeature): NavCommand(feature = feature, subRoute = feature.subRoute)

    class ContentDetail(feature: NavFeature): NavCommand(feature = feature, "detail", listOf(NavArg.Id)) {
        fun createNavRout(id: Int) = "${feature.route}/$subRoute/$id"
    }

    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(feature.route, subRoute).plus(argKeys).joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}

enum class NavArg(val key: String, val navType: NavType<*>) {
    Id("id", NavType.IntType)
}