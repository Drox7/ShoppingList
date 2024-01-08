package com.drox7.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CategoryItem)
    @Delete
    suspend fun deleteItem(item: CategoryItem)

    @Query("SELECT * FROM category_table ORDER BY name")
    fun getAllItem() : Flow<List<CategoryItem>>
}