package com.drox7.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface UnitItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: UnitItem)
    @Delete
    suspend fun deleteItem(item: UnitItem)

    @Query("SELECT * FROM unit_item_table ORDER BY localName DESC")
    fun getAllItem() : Flow<List<UnitItem>>
}