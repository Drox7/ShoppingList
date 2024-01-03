package com.drox7.myapplication.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ShoppingListItem::class,
        AddItem::class,
        NoteItem::class,
        CategoryItem::class,
    ],
    autoMigrations = [AutoMigration(from = 2, to = 3)],
    version = 3,
    exportSchema = true
)
abstract class MainDb : RoomDatabase() {
    abstract val shoppingListDao: ShoppingListDao
    abstract val noteDao: NoteDao
    abstract val addItemDao: AddItemDao
}