package com.applications.toms.mimetodoplanificado.data.datasource.cycle

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.applications.toms.mimetodoplanificado.data.model.MyCycleDatabaseModel

@Dao
interface CycleDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MyCycleDatabaseModel): Long

    @Query("SELECT * FROM cycle_table LIMIT 1")
    fun getCycle(): MyCycleDatabaseModel?

    @Query("DELETE FROM cycle_table WHERE id = :id")
    fun deleteCycle(id: Int): Int

    @Update
    fun updateCycle(item: MyCycleDatabaseModel): Int
}