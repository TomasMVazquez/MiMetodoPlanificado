package com.applications.toms.mimetodoplanificado.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.applications.toms.mimetodoplanificado.data.datasource.cycle.CycleDatabaseDao
import com.applications.toms.mimetodoplanificado.data.datasource.method.MethodDatabaseDao
import com.applications.toms.mimetodoplanificado.data.datasource.painscale.PainScaleDatabaseDao
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod
import com.applications.toms.mimetodoplanificado.data.model.MyCycleDatabaseModel
import com.applications.toms.mimetodoplanificado.data.model.PainScaleDatabaseModel

@Database(entities = [ChosenMethod::class], version = 1, exportSchema = false)
abstract class MyMethodDatabase: RoomDatabase() {

    abstract fun databaseDao(): MethodDatabaseDao

}

@Database(entities = [MyCycleDatabaseModel::class], version = 1, exportSchema = false)
abstract class MyCycleDatabase: RoomDatabase() {

    abstract fun databaseDao(): CycleDatabaseDao

}

@Database(entities = [PainScaleDatabaseModel::class], version = 1, exportSchema = false)
abstract class MyPainScaleDatabase: RoomDatabase() {

    abstract fun databaseDao(): PainScaleDatabaseDao

}