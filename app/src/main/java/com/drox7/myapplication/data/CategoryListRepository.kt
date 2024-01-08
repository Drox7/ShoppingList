package com.drox7.myapplication.data

import kotlinx.coroutines.flow.Flow

interface CategoryListRepository {
        suspend fun insertItem(item: CategoryItem)
        suspend fun deleteItem(item: CategoryItem)
        fun getAllItem() : Flow<List<CategoryItem>>

}