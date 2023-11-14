package dev.project.serinochallenge.ui.composable.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.project.serinochallenge.data.SerinoRepository
import dev.project.serinochallenge.data.item.entities.ProductEntities
import dev.project.serinochallenge.data.local.dao.SerinoDao
import dev.project.serinochallenge.data.local.pagingSource.DummyPagingSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SerinoRepository,
    private val serinoDao: SerinoDao
) : ViewModel() {

    val pager = Pager(PagingConfig(pageSize = 10)) {
        DummyPagingSource(repository,serinoDao)
    }.flow

    val listUiState: StateFlow<MainUiState> =
        repository.getListLocal().map { MainUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2_000L),
                initialValue = MainUiState()
            )
}

data class MainUiState(val itemList: List<ProductEntities> = listOf(), val isLoading: Boolean = false)