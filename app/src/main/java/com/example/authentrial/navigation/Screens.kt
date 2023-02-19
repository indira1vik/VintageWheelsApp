package com.example.authentrial.navigation

sealed class Screens(val route: String) {
    object ProfileScreen : Screens("profile_screen")
    object LoginScreen : Screens("login_screen")
    object SignupScreen : Screens("signup_screen")
    object NewProfileScreen : Screens("new_profile_screen")
    object HomeScreen : Screens("home_screen")
    object SplashScreen : Screens("splash_screen")
}
