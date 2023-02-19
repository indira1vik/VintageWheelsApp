package com.example.authentrial.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.authentrial.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController : NavController, auth: FirebaseAuth) {
    Box(modifier = Modifier
        .fillMaxSize()){
        Image(
            modifier = Modifier.blur(5.dp),
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "bg",
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(key1 = true){
                delay(2000)
                navController.popBackStack()
                if (auth.currentUser != null){
                    navController.navigate("home_screen")
                } else{
                    navController.navigate("login_screen")
                }
            }
            Text(
                text = "VintageWheels",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 42.sp,
                color = Color.White
            )
            Text(text = "From Onefactorial",color = Color.White)
        }
    }

}

//@Preview
//@Composable
//fun Preview(){
//    SplashScreen(rememberNavController(), auth = FirebaseAuth.getInstance())
//}