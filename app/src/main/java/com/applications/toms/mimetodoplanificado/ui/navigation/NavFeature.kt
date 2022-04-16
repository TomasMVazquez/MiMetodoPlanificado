package com.applications.toms.mimetodoplanificado.ui.navigation

enum class NavFeature(val route: String, val subRoute: String = "") {
    HOME("home"),
    ON_BOARDING("home","onBoarding"),
    ABOUT_US("home","about"),
    MY_METHOD("home","myMethod"),
    ALARM_SETTINGS("home","alarmSettings")
}