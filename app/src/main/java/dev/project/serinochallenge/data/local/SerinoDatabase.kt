package dev.project.serinochallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.project.serinochallenge.data.item.entities.ProductEntities
import dev.project.serinochallenge.data.local.converter.SerinoEntityConverter
import dev.project.serinochallenge.data.local.dao.SerinoDao

@Database(
    entities = [ProductEntities::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(SerinoEntityConverter::class)
abstract class SerinoDatabase : RoomDatabase() {
    abstract fun serinoDao(): SerinoDao
}