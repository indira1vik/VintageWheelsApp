package com.example.authentrial.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.authentrial.repo.MainView
import com.example.authentrial.screens.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NavGraph(navController: NavHostController, auth: FirebaseAuth,viewModel: MainView) {
    NavHost(navController = navController, startDestination = "splash_screen")
    {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController,auth)
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(navController,auth)
        }
        composable(Screens.SignupScreen.route) {
            SignupScreen(navController,auth)
        }
        composable(Screens.NewProfileScreen.route) {
            NewProfileScreen(navController,auth)
        }
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController,auth,viewModel)
        }
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController,auth)
        }
    }
}