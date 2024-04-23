package com.plus.simplefylist.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.plus.simplefylist.data.entities.ListEntity
import com.plus.simplefylist.data.entities.ListProductCrossRef
import com.plus.simplefylist.data.entities.ProductEntity

data class ListWithProductsModel(
    @Embedded val list: ListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ListProductCrossRef::class,
            parentColumn = "listId",
            entityColumn = "productId"
        )
    )
    val products: List<ProductEntity>
)
