package com.applications.toms.mimetodoplanificado.ui.navigation

enum class NavFeature(val route: String, val subRoute: String = "") {
    HOME("home"),
    ON_BOARDING("home","onboarding"),
    ABOUT_US("home","about")
}