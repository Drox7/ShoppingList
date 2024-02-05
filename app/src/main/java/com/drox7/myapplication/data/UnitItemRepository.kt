package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

interface UnitItemRepository {
    suspend fun insertItem(item: UnitItem)
    suspend fun deleteItem(item: UnitItem)
    fun getAllItem() : Flow<List<UnitItem>>
}