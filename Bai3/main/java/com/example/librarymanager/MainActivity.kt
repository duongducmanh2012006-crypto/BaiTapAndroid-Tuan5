package com.example.librarymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LibraryApp()
            }
        }
    }
}

private data class TabItem(val route: String, val label: String, val icon: String)

private val tabs = listOf(
    TabItem("manage", "Quản lý", "🏠"),
    TabItem("books", "DS Sách", "📚"),
    TabItem("students", "Sinh viên", "👤")
)

@Composable
fun LibraryApp() {
    val nav = rememberNavController()
    val vm: LibraryViewModel = viewModel()
    val current by nav.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = current?.destination?.route == tab.route,
                        onClick = {
                            nav.navigate(tab.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo("manage") { saveState = true }
                            }
                        },
                        icon = { Text(tab.icon) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = "manage",
            modifier = Modifier.padding(padding)
        ) {
            composable("manage") { ManageScreen(vm) }
            composable("books") { BooksScreen(vm) }
            composable("students") { StudentsScreen(vm) }
        }
    }
}