package com.example.callingapp.ui.screens

import android.Manifest
import android.net.http.SslCertificate
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.callingapp.ui.screens.DialpadScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dialpad : Screen("dialpad", "Dialpad", Icons.Default.Dialpad)
    object Recents : Screen("recents", "Recents", Icons.Default.History)
    object Contacts : Screen("contacts", "Contacts", Icons.Default.Contacts)
}

val items = listOf(
    Screen.Dialpad,
    Screen.Recents,
    Screen.Contacts,
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.CALL_PHONE,
        )
    )

    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }


    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    SslCertificate.saveState = true
                                }
                                launchSingleTop = true
                                SslCertificate.restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Dialpad.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dialpad.route) { DialpadScreen() }
            composable(Screen.Recents.route) { RecentsScreen() }
            composable(Screen.Contacts.route) { ContactsScreen() }
        }
    }
}
