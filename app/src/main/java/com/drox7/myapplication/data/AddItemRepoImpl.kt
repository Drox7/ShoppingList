package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

class AddItemRepoImpl(
    private val dao: AddItemDao
) :AddItemRepository {
    override suspend fun insertItem(item: AddItem) {
        dao.insertItem(item)
    }

    override suspend fun insertItem(item: ShoppingListItem) {
        dao.insertItem(item)
    }

    override suspend fun insertItem(item: TransactionItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: AddItem) {
        dao.deleteItem(item)
    }

    override fun getAllItemById(listId: Int): Flow<List<AddItem>> {
        return dao.getAllItemById(listId)
    }

    override suspend fun getListItemById(listId: Int): ShoppingListItem {
        return dao.getListItemById(listId)
    }
}