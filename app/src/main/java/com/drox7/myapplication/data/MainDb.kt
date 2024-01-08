package com.drox7.myapplication.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
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
            from = 4,
            to = 5,
          //  spec = MainDb.RenameShopList::class
        )
    ],
    version = 5,
    exportSchema = true
)
abstract class MainDb : RoomDatabase() {
    // @RenameTable(fromTableName = "shop_list_name", toTableName ="shop_list_table") Rename one Table
//    @RenameTable.Entries( // Rename several tables
//        RenameTable(fromTableName = "shop_list_name", toTableName ="shop_list_table"),
//        RenameTable(fromTableName = "add_item", toTableName ="add_item_table"),
//    )
//    @RenameColumn( Rename one column
//        "shop_list_table",
//        fromColumnName = "time",
//        toColumnName = "changeTime"
//    )

   // class RenameShopList : AutoMigrationSpec

    abstract val shoppingListDao: ShoppingListDao
    abstract val categoryListDao: CategoryListDao
    abstract val noteDao: NoteDao
    abstract val addItemDao: AddItemDao
}