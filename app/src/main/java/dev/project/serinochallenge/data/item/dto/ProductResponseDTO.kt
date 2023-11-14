package dev.project.serinochallenge.data.item.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponseDTO(
    @Json(name = "products")
    val products: List<Product> = listOf(),
    @Json(name = "total")
    val total: Int = 0,
    @Json(name = "skip")
    val skip: Int = 0,
    @Json(name = "limit")
    val limit: Int = 0
)