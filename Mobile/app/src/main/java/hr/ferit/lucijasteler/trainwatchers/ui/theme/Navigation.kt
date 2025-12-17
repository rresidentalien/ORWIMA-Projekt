package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Destination(
    val route: String,
    val icon: ImageVector,
    val contentDescription : String
) {
    HOME_SCREEN("home", Icons.Outlined.Home, "Home"),
    ADD_NEW_SCREEN("add_new", Icons.Outlined.Add, "Add New"),
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME_SCREEN -> HomeScreen()
                    Destination.ADD_NEW_SCREEN -> AddNew()
                }
            }
        }
    }
}

@Preview()
@Composable
fun BottomBar() {
    val navController = rememberNavController()
    val startDestination = Destination.HOME_SCREEN
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = DarkAntiqueWhite,
                modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            ) {
                Destination.entries.forEachIndexed { index, destination ->
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
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.contentDescription) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
    }
}