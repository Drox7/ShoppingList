package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

class CategoryListImpl(
    private val dao: CategoryListDao
) : CategoryListRepository{
    override suspend fun insertItem(item: CategoryItem) {
     dao.insertItem(item)
    }

    override suspend fun deleteItem(item: CategoryItem) {
        dao.deleteItem(item)
    }

    override fun getAllItem(): Flow<List<CategoryItem>> {
      return  dao.getAllItem()
    }

}