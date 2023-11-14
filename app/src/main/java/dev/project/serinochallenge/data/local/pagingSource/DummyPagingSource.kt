package dev.project.serinochallenge.data.local.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.project.serinochallenge.data.SerinoRepository
import dev.project.serinochallenge.data.item.dto.toProductDB
import dev.project.serinochallenge.data.item.entities.ProductEntities
import dev.project.serinochallenge.data.local.dao.SerinoDao
import java.io.IOException
import javax.inject.Inject

class DummyPagingSource @Inject constructor(
    private val repository: SerinoRepository,
    private val dao: SerinoDao
) : PagingSource<Int, ProductEntities>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntities> {
        return try {
            val nextIdNumber = params.key ?: 0
            val localData = repository.fetchDataFromRemote(nextIdNumber)
            val products = localData.body()?.products

            if (products!!.isNotEmpty()) {
                products.forEach {
                    dao.insert(it.toProductDB())
                }
            }

            LoadResult.Page(
                data = localData.body()!!.products.map { it.toProductDB() },
                prevKey = params.prevKey(),
                nextKey = params.nextKey(localData.body()!!.total)
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductEntities>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
}

fun PagingSource.LoadParams<Int>.prevKey() =
    (key?.coerceAtLeast(0) ?: 0).takeIf { it > 0 }?.minus(loadSize)?.coerceAtLeast(0)

fun PagingSource.LoadParams<Int>.nextKey(total: Int) =
    (key?.coerceAtLeast(0) ?: 0).plus(10).takeIf { it <= total }