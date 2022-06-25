package com.applications.toms.mimetodoplanificado.data.datasource.painscale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.applications.toms.mimetodoplanificado.data.model.PainScaleDatabaseModel

@Dao
interface PainScaleDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PainScaleDatabaseModel): Long

    @Query("SELECT COUNT(date) FROM pain_scale_table WHERE date = :date")
    fun checkIfDateExistsAlready(date: String): Int

    @Query("SELECT * FROM pain_scale_table")
    fun getPainScales(): List<PainScaleDatabaseModel>

    @Query("DELETE FROM pain_scale_table WHERE date = :date")
    fun deletePainScaleFromDate(date: String): Int

    @Update
    fun updatePainScale(item: PainScaleDatabaseModel): Int

}