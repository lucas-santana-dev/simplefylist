package com.plus.simplefylist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plus.simplefylist.data.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE list_id = :listId")
    fun getAllProductsForList(listId: String): Flow<List<ProductEntity>>

    @Query("DELETE FROM product WHERE list_id = :listId")
    suspend fun deleteAllProductsForList(listId: String)

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: String): Flow<ProductEntity?>

    @Query("UPDATE product SET is_checked = :isChecked WHERE id = :productId")
    suspend fun updateProductCheckedStatus(productId: String, isChecked: Boolean)


    @Query("SELECT * FROM product WHERE is_checked = 1 AND list_id =:listId")
     fun productsCheck(listId: String): Flow<List<ProductEntity>>

    // adicionar outras consultas conforme necessário...

}