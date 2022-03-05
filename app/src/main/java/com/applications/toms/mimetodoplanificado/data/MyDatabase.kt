package com.applications.toms.mimetodoplanificado.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod

@Database(entities = [ChosenMethod::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

}