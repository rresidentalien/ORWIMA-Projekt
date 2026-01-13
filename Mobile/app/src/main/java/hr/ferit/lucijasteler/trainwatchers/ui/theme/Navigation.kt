package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hr.ferit.lucijasteler.trainwatchers.R
import hr.ferit.lucijasteler.trainwatchers.data.TrainViewModel

enum class Destination(
    val route : String,
    val contentDescription : String,
    val icon: ImageVector? = null,
    val drawable: Int? = null
) {
    HOME_SCREEN("home", "Home", icon = Icons.Outlined.Home),
    ADD_NEW_SCREEN("add_new", "Add New", icon = Icons.Outlined.Add),
    MY_TRAINS_SCREEN("my_trains", "My Trains", drawable = R.drawable.train),
    ABOUT_APP_SCREEN("about_app", "About App", icon = Icons.Outlined.Info),
    TRAIN_DETAILS_SCREEN("train_details/{trainId}", "Train Details", icon = Icons.Outlined.Info)
}

@Composable
fun AppNavHost(navController: NavHostController, startDestination: Destination, viewModel: TrainViewModel, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(Destination.HOME_SCREEN.route) { HomeScreen(navController, viewModel) }
        composable(Destination.ADD_NEW_SCREEN.route) { AddNew(viewModel) }
        composable(Destination.MY_TRAINS_SCREEN.route) { MyTrains(navController, viewModel) }
        composable(Destination.ABOUT_APP_SCREEN.route) { AboutApp() }
        composable(
            route = Destination.TRAIN_DETAILS_SCREEN.route,
            arguments = listOf(navArgument("trainId") { type = NavType.StringType })
        ) { backStackEntry ->
            val trainId = backStackEntry.arguments?.getString("trainId")
            TrainDetails(viewModel.trains.find { it.id == trainId }!!)
        }
    }
}

@Composable
fun AppNavigation(viewModel: TrainViewModel) {
    val navController = rememberNavController()
    val startDestination = Destination.HOME_SCREEN
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    val destinations = listOf(Destination.HOME_SCREEN, Destination.ADD_NEW_SCREEN, Destination.MY_TRAINS_SCREEN, Destination.ABOUT_APP_SCREEN)

    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = DarkAntiqueWhite,
                modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            ) {
                destinations.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AntiqueWhite,
                            unselectedIconColor = Brown,
                            indicatorColor = Brown,
                            unselectedTextColor = Brown,
                            selectedTextColor = Brown
                        ),
                        icon = {
                            if (destination.icon != null) {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.contentDescription
                                )
                            } else if (destination.drawable != null) {
                                Icon(
                                    painter = painterResource(id = destination.drawable),
                                    contentDescription = destination.contentDescription
                                )
                            }
                        },
                        label = { Text(destination.contentDescription) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController, startDestination, viewModel, modifier = Modifier.padding(contentPadding))
    }
}
