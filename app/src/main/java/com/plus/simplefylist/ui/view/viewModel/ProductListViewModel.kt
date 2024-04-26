package com.plus.simplefylist.ui.view.viewModel

import androidx.lifecycle.ViewModel
import com.plus.simplefylist.data.dao.ListDao
import com.plus.simplefylist.data.dao.ProductDao
import com.plus.simplefylist.data.entities.ListEntity
import com.plus.simplefylist.data.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ProductListViewModel(private val listDao: ListDao, private val productDao: ProductDao) :
    ViewModel() {


    suspend fun updateCheck(productId: String, isChecked: Boolean) {
        productDao.updateProductCheckedStatus(productId, isChecked)
    }

    fun getAllProductForList(listId: String): Flow<List<ProductEntity>> {
        return productDao.getAllProductsForList(listId)
    }

    fun getAllProductCheckedForList(listId: String): Flow<List<ProductEntity>> {
        return productDao.productsCheck(listId)
    }

    fun getList(listId: String): Flow<ListEntity?> {
        return listDao.getListById(listId)
    }

    suspend fun deleteProducts(listId: String) {
        productDao.deleteAllProductsForList(listId)
    }

    suspend fun deleteList(listId: String) {
        val list = listDao.getListById(listId).firstOrNull()
        list?.let {
            listDao.delete(it)
        }

    }

    fun calcularTotalLista(listId: String): Flow<Double> {
        return getAllProductCheckedForList(listId).map { productList ->
            var total = 0.0
            productList.forEach { product ->
                val price = product.productPrice.toDoubleOrNull() ?: 0.0
                val quantity = product.productQuantity
                total += price * quantity
            }
            total
        }
    }


}