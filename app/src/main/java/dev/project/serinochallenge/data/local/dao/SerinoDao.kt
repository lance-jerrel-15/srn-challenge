package dev.project.serinochallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.project.serinochallenge.data.item.entities.ProductEntities
import kotlinx.coroutines.flow.Flow

@Dao
interface SerinoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productEntities: ProductEntities)

    @Query("SELECT * FROM product_lists")
    fun getList(): Flow<List<ProductEntities>>
}