package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

class UnitItemImpl(
private val dao: UnitItemDao
) : UnitItemRepository {
    override suspend fun insertItem(item: UnitItem) {
        dao.insertItem(item)
    }
    override suspend fun deleteItem(item: UnitItem) {
        dao.deleteItem(item)
    }
    override fun getAllItem(): Flow<List<UnitItem>> {
        return dao.getAllItem()
    }
}