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
    @Query("SELECT ifnull(category_table.name,\"Без категории\") as category, sum(sum) as sum FROM transaction_item_table left join category_table on transaction_item_table.categoryId = category_table.id group BY category_table.name,transaction_item_table.categoryId")
    fun getSummaryItems() : Flow<List<SummaryItem>>
}