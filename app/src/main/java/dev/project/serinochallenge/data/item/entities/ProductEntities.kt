package dev.project.serinochallenge.data.item.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "product_lists")
data class ProductEntities(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "price") val price: Int = 0,
    @ColumnInfo(name = "discountPercentage") val discountPercentage: Double = 0.0,
    @ColumnInfo(name = "rating") val rating: Double = 0.0,
    @ColumnInfo(name = "stock") val stock: Int = 0,
    @ColumnInfo(name = "brand") val brand: String = "",
    @ColumnInfo(name = "category") val category: String = "",
    @ColumnInfo(name = "thumbnail") val thumbnail: String = "",
    @ColumnInfo(name = "images") val images: List<Images> = listOf()
) {

    @JsonClass(generateAdapter = true)
    data class Images(
        @Json(name = "fullPath")
        val name: String = ""
    )
}
