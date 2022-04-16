package com.applications.toms.mimetodoplanificado.di

import android.app.Application
import androidx.room.Room
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.mimetodoplanificado.data.MyDatabase
import com.applications.toms.mimetodoplanificado.data.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MyDatabase::class.java,
        "method_table"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MyDatabase): LocalDataSource = RoomDataSource(db)

}