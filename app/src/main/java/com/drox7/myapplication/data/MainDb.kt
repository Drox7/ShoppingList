package com.drox7.myapplication.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [
        ShoppingListItem::class,
        AddItem::class,
        NoteItem::class,
        CategoryItem::class,
    ],
    autoMigrations = [
        AutoMigration(
            from = 3,
            to = 4,
            spec = MainDb.RenameShopList::class
        )
                     ],
    version = 4,
    exportSchema = true
)
abstract class MainDb : RoomDatabase() {
    // @RenameTable(fromTableName = "shop_list_name", toTableName ="shop_list_table") Rename one Table
    @RenameTable.Entries( // Rename several tables
        RenameTable(fromTableName = "shop_list_name", toTableName ="shop_list_table"),
        RenameTable(fromTableName = "add_item", toTableName ="add_item_table"),
    )
    class RenameShopList: AutoMigrationSpec
    abstract val shoppingListDao: ShoppingListDao
    abstract val noteDao: NoteDao
    abstract val addItemDao: AddItemDao
}