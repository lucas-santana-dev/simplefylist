package com.plus.simplefylist.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["listId", "productId"])
data class ListProductCrossRef(
    val listId: String,
    val productId: String
)