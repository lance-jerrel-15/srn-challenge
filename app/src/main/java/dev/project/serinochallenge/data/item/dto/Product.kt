package dev.project.serinochallenge.data.item.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.project.serinochallenge.data.item.entities.ProductEntities

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "description")
    val description: String = "",
    @Json(name = "price")
    val price: Int = 0,
    @Json(name = "discountPercentage")
    val discountPercentage: Double = 0.0,
    @Json(name = "rating")
    val rating: Double = 0.0,
    @Json(name = "stock")
    val stock: Int = 0,
    @Json(name = "brand")
    val brand: String = "",
    @Json(name = "category")
    val category: String = "",
    @Json(name = "thumbnail")
    val thumbnail: String = "",
    @Json(name = "images")
    val images: List<String> = listOf()
)

fun Product.toProductDB() : ProductEntities {
    return ProductEntities(id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        brand = brand,
        category = category,
        thumbnail = thumbnail,
        images = images.map { ProductEntities.Images(it) }
    )
}
