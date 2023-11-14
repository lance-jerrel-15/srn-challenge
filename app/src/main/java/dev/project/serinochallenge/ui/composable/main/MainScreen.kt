package dev.project.serinochallenge.ui.composable.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import dev.project.serinochallenge.R
import dev.project.serinochallenge.base.navigation.NavigationDestination
import dev.project.serinochallenge.base.navigation.TodoTopAppBar
import dev.project.serinochallenge.ui.composable.IndeterminateCircularIndicator
import dev.project.serinochallenge.ui.composable.ProductItem

object MainDestination : NavigationDestination {
    override val route: String = "main"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigateToDetailsScreen: (Int) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val dummy = viewModel.pager.collectAsLazyPagingItems()
    val productList by viewModel.listUiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = stringResource(id = MainDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Column(Modifier.fillMaxSize()) {
                when (dummy.loadState.refresh) {
                    LoadState.Loading -> { IndeterminateCircularIndicator(true) }
                    else -> {
                        if (isOnline(context)) {
                            LazyColumn {
                                itemsIndexed(dummy) { _, item ->
                                    item?.let {
                                        ProductItem(
                                            item = item,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 15.dp),
                                            onItemClick = navigateToDetailsScreen
                                        )
                                    }
                                }
                            }
                        } else {
                            if (productList.itemList.isNotEmpty()) {
                                LazyColumn {
                                    items(productList.itemList) { item ->
                                        ProductItem(
                                            item = item,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 15.dp),
                                            onItemClick = navigateToDetailsScreen
                                        )
                                    }
                                }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.no_item_found),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentHeight()
                                    )
                                }
                            }
                        }
                        IndeterminateCircularIndicator(false)
                    }
                }
            }
        }
    }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen({})
}