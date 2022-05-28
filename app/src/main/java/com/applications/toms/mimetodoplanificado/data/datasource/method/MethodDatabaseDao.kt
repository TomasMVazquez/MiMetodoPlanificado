package com.applications.toms.mimetodoplanificado.data.datasource.method

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod

@Dao
interface MethodDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ChosenMethod): Long

    @Query("SELECT * FROM method_table LIMIT 1")
    fun getMethod(): ChosenMethod?

    @Query("DELETE FROM method_table WHERE methodChosen = :method")
    fun deleteMethod(method: Method): Int

    @Update
    fun updateMethod(item: ChosenMethod): Int
}