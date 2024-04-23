package com.plus.simplefylist.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.plus.simplefylist.data.dao.ListDao
import com.plus.simplefylist.data.dao.ProductDao
import com.plus.simplefylist.data.entities.ListEntity
import com.plus.simplefylist.data.entities.ListProductCrossRef
import com.plus.simplefylist.data.entities.ProductEntity

@Database(
    entities =
    [
        ProductEntity::class,
        ListEntity::class,
        ListProductCrossRef::class
    ],
    version = 3,
    autoMigrations = [AutoMigration(1, 2), AutoMigration(2,3)],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun listDao(): ListDao


}

