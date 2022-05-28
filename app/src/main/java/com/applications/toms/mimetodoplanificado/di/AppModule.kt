package com.applications.toms.mimetodoplanificado.di

import android.app.Application
import androidx.room.Room
import com.applications.toms.data.source.LocalCycleDataSource
import com.applications.toms.data.source.LocalMethodDataSource
import com.applications.toms.mimetodoplanificado.data.MyCycleDatabase
import com.applications.toms.mimetodoplanificado.data.MyMethodDatabase
import com.applications.toms.mimetodoplanificado.data.cycle.CycleRoomDataSource
import com.applications.toms.mimetodoplanificado.data.method.MethodRoomDataSource
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
        MyMethodDatabase::class.java,
        "method_table"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MyMethodDatabase): LocalMethodDataSource = MethodRoomDataSource(db)

    @Provides
    fun localCycleDataSourceProvider(db: MyCycleDatabase): LocalCycleDataSource = CycleRoomDataSource(db)
}