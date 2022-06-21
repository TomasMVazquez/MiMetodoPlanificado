package com.applications.toms.mimetodoplanificado.di

import android.app.Application
import androidx.room.Room
import com.applications.toms.data.source.LocalCycleDataSource
import com.applications.toms.data.source.LocalMethodDataSource
import com.applications.toms.data.source.LocalPainScaleDataSource
import com.applications.toms.mimetodoplanificado.data.MyCycleDatabase
import com.applications.toms.mimetodoplanificado.data.MyMethodDatabase
import com.applications.toms.mimetodoplanificado.data.MyPainScaleDatabase
import com.applications.toms.mimetodoplanificado.data.datasource.cycle.CycleRoomDataSource
import com.applications.toms.mimetodoplanificado.data.datasource.method.MethodRoomDataSource
import com.applications.toms.mimetodoplanificado.data.datasource.painscale.PainScaleRoomDataSource
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
    fun methodDatabaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MyMethodDatabase::class.java,
        "method_table"
    ).build()

    @Provides
    @Singleton
    fun cycleDatabaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MyCycleDatabase::class.java,
        "cycle_table"
    ).build()

    @Provides
    @Singleton
    fun painScaleDatabaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MyPainScaleDatabase::class.java,
        "pain_scale_table"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MyMethodDatabase): LocalMethodDataSource = MethodRoomDataSource(db)

    @Provides
    fun localCycleDataSourceProvider(db: MyCycleDatabase): LocalCycleDataSource = CycleRoomDataSource(db)

    @Provides
    fun localPainScaleDataSourceProvider(db: MyPainScaleDatabase): LocalPainScaleDataSource = PainScaleRoomDataSource(db)
}