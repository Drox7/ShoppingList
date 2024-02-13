package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

class TransactionItemImpl(
private val dao: TransactionItemDao
) : TransactionItemRepository {
    override suspend fun insertItem(item: TransactionItem) {
        dao.insertItem(item)
    }
    override suspend fun deleteItem(item: TransactionItem) {
        dao.deleteItem(item)
    }
    override fun getAllItem(): Flow<List<TransactionItem>> {
        return dao.getAllItem()
    }
    override fun getSummaryItems(): Flow<List<SummaryItem>> {
        return dao.getSummaryItems()
    }
}