package com.gzeinnumer.junit4part5part6room.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gzeinnumer.junit4part5part6room.local.ShoppingDao
import com.gzeinnumer.junit4part5part6room.local.ShoppingItem

//todo 3
@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}