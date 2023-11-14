package dev.project.serinochallenge.data

import dev.project.serinochallenge.data.item.entities.ProductEntities
import dev.project.serinochallenge.data.local.dao.SerinoDao
import dev.project.serinochallenge.data.remote.SerinoService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SerinoRepository @Inject constructor(
    private val serinoService: SerinoService,
    private val serinoDao: SerinoDao
) {

    suspend fun fetchDataFromRemote(nextLastIdNumber: Int) = serinoService.getList(skip = nextLastIdNumber)

    fun getListLocal(): Flow<List<ProductEntities>> {
        return serinoDao.getList()
    }
}