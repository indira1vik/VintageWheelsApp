package com.example.authentrial.screens

import android.content.ContentValues.TAG
import android.provider.SyncStateContract
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentrial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.lang.Error

@Composable
fun nameFieldWithIcons() : String {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "emailIcon") },
        onValueChange = {
            text = it
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        singleLine = true,
        label = { Text(text = "Username") },
        placeholder = { Text(text = "Enter Username") },
    )
    return text
}

@Composable
fun SignupScreen(navController : NavController, auth: FirebaseAuth){
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentScale = ContentScale.FillBounds,
            contentDescription = "bg"
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign-up",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            val email = textFieldWithIcons()
            val pass = passwordFieldWithIcons()
            val confirm = passwordFieldWithIcons()
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue, contentColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                onClick = {
                    if (confirm == pass) {
                        Log.d(TAG, "Both the fields are equal")
                        auth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    navController.navigate("new_profile_screen")
                                }else{
                                    Log.d(TAG,"Cannot create user")
                                }
                            }
                    } else if (confirm != pass) {
                        Log.d(TAG,"Password does not match")
                    } else {
                        Log.d(TAG, "Cannot create user")
                    }
                }) {
                Text(text = "Register")
            }
        }
    }

}