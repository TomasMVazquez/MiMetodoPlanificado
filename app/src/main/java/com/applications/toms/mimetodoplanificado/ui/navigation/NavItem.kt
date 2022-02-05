package com.applications.toms.mimetodoplanificado.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

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
        listOf(feature.route)
            .plus(subRoute)
            .plus(argKeys)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}

enum class NavArg(val key: String, val navType: NavType<*>) {
    Id("id", NavType.IntType)
}