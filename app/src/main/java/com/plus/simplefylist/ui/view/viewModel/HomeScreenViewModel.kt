package com.plus.simplefylist.ui.view.viewModel

import androidx.lifecycle.ViewModel
import com.plus.simplefylist.data.dao.ListDao
import com.plus.simplefylist.data.entities.ListEntity
import kotlinx.coroutines.flow.Flow

class HomeScreenViewModel(private val listDao: ListDao) : ViewModel() {

    val allLists: Flow<List<ListEntity>> = listDao.getAllLists()
    suspend fun saveList(nome: String): ListEntity? {
        return try {
            val listEntity = ListEntity(listName = nome)
            listDao.insert(listEntity)
            listEntity
        } catch (e: Exception) {
            null
        }
    }

}