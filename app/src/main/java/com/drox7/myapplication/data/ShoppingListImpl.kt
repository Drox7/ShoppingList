package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

class ShoppingListImpl(
    private val dao: ShoppingListDao
) : ShoppingListRepository{
    override suspend fun insertItem(item: ShoppingListItem) {
     dao.insertItem(item)
    }

    override suspend fun deleteItem(item: ShoppingListItem) {
        dao.deleteItem(item)
    }

    override fun getAllItem(): Flow<List<ShoppingListItem>> {
      return  dao.getAllItem()
    }

}