package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
        suspend fun insertItem(item: ShoppingListItem)
        suspend fun deleteItem(item: ShoppingListItem)
        fun getAllItem() : Flow<List<ShoppingListItem>>

}