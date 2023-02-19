package com.example.authentrial

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.authentrial.navigation.NavGraph
import com.example.authentrial.repo.MainView
import com.example.authentrial.screens.HomeScreen
import com.example.authentrial.ui.theme.AuthenTrialTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitAll

class MainActivity : ComponentActivity() {
    private val auth by lazy{
        Firebase.auth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: MainView by viewModels()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AuthenTrialTheme {
                val navHostController: NavHostController = rememberNavController()
                NavGraph(navController = navHostController, auth = Firebase.auth, viewModel = viewModel)
            }
        }
    }
}