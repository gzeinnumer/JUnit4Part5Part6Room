package com.gzeinnumer.junit4part5part6room.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//todo 1
@Entity(tableName = "shopping_items")
data class ShoppingItem(
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)