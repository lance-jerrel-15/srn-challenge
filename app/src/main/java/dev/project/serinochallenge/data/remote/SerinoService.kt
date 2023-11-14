package dev.project.serinochallenge.data.remote

import dev.project.serinochallenge.data.item.dto.ProductResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SerinoService {

    @GET("products")
    suspend fun getList(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 10
    ): Response<ProductResponseDTO>
}