package com.applications.toms.mimetodoplanificado.ui.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

fun getSharedPreferences(context: Context): SharedPreferences{
    return context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}

object SharedPreferencesKeys {
    const val ON_BOARDING = "onBoarding"
}

/**
 * To start or not the onBoarding when initializing the app
 */
fun onBoardingHasFinished(context: Context) {
    getSharedPreferences(context).edit{ putBoolean(SharedPreferencesKeys.ON_BOARDING,true) }
}

fun hasOnBoardingAlreadyShown(context: Context): Boolean {
    return getSharedPreferences(context).getBoolean(SharedPreferencesKeys.ON_BOARDING,false)
}
