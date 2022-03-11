package com.applications.toms.mimetodoplanificado.ui.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

fun getSharedPreferences(context: Context): SharedPreferences{
    return context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}

object SharedPreferencesKeys {
    const val ON_BOARDING = "onBoarding"
    const val ON_METHOD_SAVED = "onMethodSaved"
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

/**
 * To check if a method is saved or not
 */
fun onMethodHasBeenSaved(context: Context) {
    getSharedPreferences(context).edit{ putBoolean(SharedPreferencesKeys.ON_METHOD_SAVED,true) }
}

fun isMethodSaved(context: Context): Boolean {
    return getSharedPreferences(context).getBoolean(SharedPreferencesKeys.ON_METHOD_SAVED,false)
}