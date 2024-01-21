package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

interface TransactionItemRepository {
    suspend fun insertItem(item: TransactionItem)
    suspend fun deleteItem(item: TransactionItem)
    fun getAllItem() : Flow<List<TransactionItem>>
}