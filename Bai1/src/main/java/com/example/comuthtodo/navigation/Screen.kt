package com.example.comuthtodo.navigation

/** Mỗi màn hình là một đối tượng route (sealed class thay vì chuỗi rời rạc). */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
}
