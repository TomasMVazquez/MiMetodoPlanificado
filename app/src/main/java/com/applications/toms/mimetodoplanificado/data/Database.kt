package com.applications.toms.mimetodoplanificado.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod

@Database(entities = [ChosenMethod::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase()     {
    abstract val databaseDao: DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            MyDatabase::class.java,
                            "method_table"
                    )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}