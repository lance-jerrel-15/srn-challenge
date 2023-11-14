package dev.project.serinochallenge.ui

import dev.project.serinochallenge.data.item.entities.ProductEntities

data class CurrentUiState(
    var model: ProductEntities = ProductEntities()
)