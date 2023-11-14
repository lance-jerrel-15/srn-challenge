package dev.project.serinochallenge.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.project.serinochallenge.data.item.dto.Product
import dev.project.serinochallenge.data.item.entities.ProductEntities

class SerinoEntityConverter {

    @TypeConverter
    fun fromStringToProductList(value: String): List<Product>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<Product>>(Types.newParameterizedType(List::class.java, Product::class.java))
            .fromJson(value)

    @TypeConverter
    fun fromProductListTypeToString(serino: List<Product>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<Product>>(Types.newParameterizedType(List::class.java, Product::class.java))
            .toJson(serino)

    @TypeConverter
    fun fromStringToImagesList(value: String) : List<ProductEntities.Images>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<ProductEntities.Images>>(Types.newParameterizedType(List::class.java, ProductEntities.Images::class.java))
            .fromJson(value)

    @TypeConverter
    fun fromImagesList(tagList: List<ProductEntities.Images>) : String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<ProductEntities.Images>>(Types.newParameterizedType(List::class.java, ProductEntities.Images::class.java))
            .toJson(tagList)


}