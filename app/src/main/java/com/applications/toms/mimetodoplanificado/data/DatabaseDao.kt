package com.applications.toms.mimetodoplanificado.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ChosenMethod): Long

}