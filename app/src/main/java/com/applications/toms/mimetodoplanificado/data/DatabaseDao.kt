package com.applications.toms.mimetodoplanificado.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ChosenMethod): Long

    @Query("SELECT * FROM method_table LIMIT 1")
    fun getMethod(): ChosenMethod?

}