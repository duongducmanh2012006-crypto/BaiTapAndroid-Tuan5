package com.example.comuthtodo.navigation
import com.example.comuthtodo.data.TaskRepository
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.comuthtodo.data.AppRepository
import com.example.comuthtodo.ui.screens.HomeScreen
import com.example.comuthtodo.ui.screens.OnboardingScreen
import com.example.comuthtodo.ui.screens.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val appInfo = AppRepository.appInfo

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(appInfo) {
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(appInfo, AppRepository.onboardingPages) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Home.route) {
            HomeScreen(appInfo, TaskRepository.tasks)
        }
    }
}
