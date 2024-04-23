package com.plus.simplefylist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("product")
data class ProductEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "product_price") val productPrice: String,
    @ColumnInfo(name = "product_category") val productCategory: String? = null,
    @ColumnInfo(name = "product_quantity") val productQuantity: Int,
    @ColumnInfo(name = "list_id", defaultValue = "") val listId: String = "",
    @ColumnInfo(name = "is_checked", defaultValue = "false") val isChecked: Boolean = false


)