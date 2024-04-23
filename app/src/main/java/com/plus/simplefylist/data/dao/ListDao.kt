package com.plus.simplefylist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plus.simplefylist.data.entities.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: ListEntity)

    @Delete
    suspend fun delete(list:ListEntity)

    @Query("SELECT * FROM lists")
    fun getAllLists(): Flow<List<ListEntity>>

    @Query("SELECT * FROM lists WHERE id = :listId")
    fun getListById(listId: String): Flow<ListEntity?>


    // Adicione outras consultas conforme necessário...

}