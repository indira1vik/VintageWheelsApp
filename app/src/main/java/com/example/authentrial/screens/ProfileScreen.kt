package com.example.authentrial.screens

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.authentrial.R
import com.example.authentrial.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(navController : NavController, auth: FirebaseAuth){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGueaUZ5hgNTER1ufXo436oJ2rlPTMEYsDr8cGDqe6q1EJx6NoJ4otuggT9Ku0fJDZh20sFq8PS1w&usqp=CAU&ec=48600112"),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "${auth.currentUser?.displayName}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "${auth.currentUser?.email}")
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Blue,
                contentColor = White
            ),
            shape = RoundedCornerShape(24.dp),
            onClick = {
                auth.signOut()
                navController.popBackStack()
                navController.navigate("login_screen")
            }
        ) {
            Text(text = "Log-out")
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Red
            ),
            onClick = {
                auth.currentUser?.delete()
                navController.popBackStack()
                navController.navigate("signup_screen")
            }
        ) {
            Text(text = "Delete Account")
        }
    }
}