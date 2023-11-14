package dev.project.serinochallenge.ui.composable.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.project.serinochallenge.data.SerinoRepository
import dev.project.serinochallenge.ui.CurrentUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SerinoRepository
) : ViewModel() {

    var currentUiState by mutableStateOf(CurrentUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[DetailsDestination.itemIdArg])

    init {
        viewModelScope.launch {
            val item = repository.getListLocal().first().find { it.id == itemId }
            item?.let { currentUiState.model = it }
        }
    }
}