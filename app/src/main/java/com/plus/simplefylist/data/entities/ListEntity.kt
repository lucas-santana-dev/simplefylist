package com.plus.simplefylist.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "lists")
data class ListEntity(

    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "list_name") val listName: String,
)