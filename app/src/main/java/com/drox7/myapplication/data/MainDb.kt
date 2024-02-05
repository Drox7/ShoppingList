package com.drox7.myapplication.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ShoppingListItem::class,
        AddItem::class,
        NoteItem::class,
        CategoryItem::class,
        TransactionItem::class,
        CurrencyItem::class,
        UnitItem::class,
        AccountBalancesItem::class,
        AssetLiabilityItem::class,
        AccountItem::class
    ],
    autoMigrations = [
        AutoMigration(
            from = 6,
            to = 7,
          //  spec = MainDb.RenameShopList::class
        )
    ],
    version = 7,
    exportSchema = true
)
@TypeConverters(Converters::class)
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
    abstract val transactionItemDao: TransactionItemDao
    abstract val unitItemDao : UnitItemDao
}