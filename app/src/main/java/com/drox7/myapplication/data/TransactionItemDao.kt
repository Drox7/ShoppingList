package com.drox7.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface TransactionItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: TransactionItem)
    @Delete
    suspend fun deleteItem(item: TransactionItem)

    @Query("SELECT * FROM transaction_item_table ORDER BY dateTime DESC")
    fun getAllItem() : Flow<List<TransactionItem>>
}