package com.drox7.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AddItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: AddItem)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: TransactionItem)
    @Delete
    suspend fun deleteItem(item: AddItem)
    @Query("SELECT * FROM add_item_table WHERE listId = :listId")
    fun getAllItemById(listId : Int) : Flow<List<AddItem>>
    @Query("SELECT * FROM shop_list_table WHERE id = :listId")
    suspend fun getListItemById(listId: Int) : ShoppingListItem
    @Update
    suspend fun insertItem(item: ShoppingListItem)
}