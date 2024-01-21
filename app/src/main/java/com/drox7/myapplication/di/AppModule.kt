package com.drox7.myapplication.di

import android.app.Application
import androidx.room.Room
import com.drox7.myapplication.data.AddItemRepoImpl
import com.drox7.myapplication.data.AddItemRepository
import com.drox7.myapplication.data.CategoryListImpl
import com.drox7.myapplication.data.CategoryListRepository
import com.drox7.myapplication.data.MainDb
import com.drox7.myapplication.data.NoteRepoImpl
import com.drox7.myapplication.data.NoteRepository
import com.drox7.myapplication.data.ShoppingListImpl
import com.drox7.myapplication.data.ShoppingListRepository
import com.drox7.myapplication.data.TransactionItemImpl
import com.drox7.myapplication.data.TransactionItemRepository
import com.drox7.myapplication.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(app:Application) : MainDb {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "shop_list_db").build()
         //   ).createFromAsset("shop_list_db.db").build() //Create intial Db with data
    }

    @Provides
    @Singleton
    fun provideShopRepo(db: MainDb) : ShoppingListRepository{
        return ShoppingListImpl(db.shoppingListDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepo(db: MainDb) : CategoryListRepository{
        return CategoryListImpl(db.categoryListDao)
    }
    @Provides
    @Singleton
    fun provideTransactionRepo(db: MainDb) : TransactionItemRepository{
        return TransactionItemImpl(db.transactionItemDao)
    }
    @Provides
    @Singleton
    fun provideAddItemRepo(db: MainDb) : AddItemRepository{
        return AddItemRepoImpl(db.addItemDao)
    }
    @Provides
    @Singleton
    fun provideNoteRepo(db: MainDb) : NoteRepository{
        return NoteRepoImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(app: Application) : DataStoreManager{
        return DataStoreManager(app)
    }
}