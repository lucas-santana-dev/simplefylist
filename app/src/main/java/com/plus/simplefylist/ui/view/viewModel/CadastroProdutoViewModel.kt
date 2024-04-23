package com.plus.simplefylist.ui.view.viewModel

import androidx.lifecycle.ViewModel
import com.plus.simplefylist.data.dao.ProductDao
import com.plus.simplefylist.data.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

class CadastroProdutoViewModel(private val productDao: ProductDao) : ViewModel() {

    fun getProduct(productId: String): Flow<ProductEntity?> {
        return productDao.getProductById(productId)
    }



    suspend fun adicionarProduto(
        nomeProduto: String,
        quantidade: String? = "",
        categoria: String? = "",
        preco: String? = "",
        listId: String,
        productId: String? = null
    ): ProductEntity? {
        val quantidadeInt = quantidade?.toIntOrNull() ?: 1
        return try {
            if (productId.isNullOrEmpty()) {
                val produto = ProductEntity(
                    productName = nomeProduto,
                    productQuantity = quantidadeInt,
                    productCategory = categoria ?: "Sem Categoria",
                    productPrice = preco ?: "",
                    listId = listId,
                )
                productDao.insert(produto)
                produto

            } else {
                val produto = ProductEntity(
                    productName = nomeProduto,
                    productQuantity = quantidadeInt,
                    productCategory = categoria ?: "",
                    productPrice = preco ?: "",
                    listId = listId,
                    id = productId
                )
                productDao.insert(produto)
                produto
            }

        } catch (e: NumberFormatException) {
            null
        }
    }


}