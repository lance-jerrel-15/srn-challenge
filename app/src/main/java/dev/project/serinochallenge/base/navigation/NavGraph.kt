package dev.project.serinochallenge.base.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.project.serinochallenge.ui.composable.details.DetailsDestination
import dev.project.serinochallenge.ui.composable.details.DetailsScreen
import dev.project.serinochallenge.ui.composable.main.MainDestination
import dev.project.serinochallenge.ui.composable.main.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainDestination.route,
        modifier = modifier
    ) {
        composable(
            route = MainDestination.route
        ) {
            MainScreen(
                navigateToDetailsScreen = { itemId ->
                    navController.navigate("${DetailsDestination.route}/${itemId}")
                }
            )
        }
        composable(
            route = DetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailsDestination.itemIdArg) { type = NavType.IntType })
        ) {
            DetailsScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}