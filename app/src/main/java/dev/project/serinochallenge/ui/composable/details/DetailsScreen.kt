package dev.project.serinochallenge.ui.composable.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.project.serinochallenge.R
import dev.project.serinochallenge.base.navigation.NavigationDestination
import dev.project.serinochallenge.base.navigation.TodoTopAppBar
import dev.project.serinochallenge.data.item.entities.ProductEntities

object DetailsDestination : NavigationDestination {
    override val route: String = "details"
    override val titleRes: Int = R.string.details
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
    onNavigateUp: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val item = viewModel.currentUiState.model
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = item.title,
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())) {
                    BodyScreen(item)
                }
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyScreen(
    item: ProductEntities,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column {
            BodyContent(item)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyContent(
    item: ProductEntities,
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)
        ) {
            AsyncImage(
                model = item.thumbnail,
                contentScale = ContentScale.Crop,
                modifier = Modifier.widthIn(100.dp),
                contentDescription = "imageItem"
            )
        }
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Id: " + item.id
                )
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Price: " + item.price
                )
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Brand: " + item.brand
                )
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = item.category
                )
            }
        }
    }
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 5.dp),
                text = "Description: \n" + item.description
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 5.dp),
                text = "Previews:"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                val images = item.images
                images.forEach { image ->
                    Row {
                        AsyncImage(
                            model = image.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.widthIn(100.dp),
                            contentDescription = "imageItem"
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
