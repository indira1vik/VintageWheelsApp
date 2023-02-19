package com.example.authentrial.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PanoramaFishEye
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.authentrial.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun textFieldWithIcons() : String {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "emailIcon") },
        onValueChange = {
            text = it
        },
        modifier = Modifier.fillMaxWidth(0.95f),
        singleLine = true,
        label = { Text(text = "Email address") },
        placeholder = { Text(text = "Enter your e-mail") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Black,
            leadingIconColor = Color.Black
        )
    )
    return text
}

@Composable
fun passwordFieldWithIcons() : String {
    var value by remember {
        mutableStateOf("")
    }

    var showPassword by remember {
        mutableStateOf(false)
    }

    var isEmailNotValid by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            value = newText
        },
        label = { Text(text = "Password") },
        placeholder = { Text(text = "Enter your password") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = "Lock Icon"
            )
        },
        isError = isEmailNotValid,
        modifier = Modifier.fillMaxWidth(0.95f),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black
        ),
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Icons.Rounded.RemoveRedEye else Icons.Outlined.RemoveRedEye,
                    contentDescription = if (showPassword) "Show Password" else "Hide Password"
                )
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
    )
    return value
}

@Composable
fun LoginScreen(navController : NavController, auth: FirebaseAuth){
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
                text = "Sign-in",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            val email = textFieldWithIcons()
            val pass = passwordFieldWithIcons()
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(24.dp),
                onClick = {
                auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            val dataUser = FirebaseAuth.getInstance().uid
                            Log.d(TAG,"The user successfully Logged In. $dataUser")
                            navController.popBackStack()
                            navController.navigate("home_screen")
                        } else{
                            Log.d(TAG,"The user failed")
                            Toast.makeText(context,"Error!",Toast.LENGTH_SHORT).show()
                        }
                    }

            }) {
                Text(text = "Sign-in", color = Color.White)
            }
            Text(text = "Don't Have Account?")
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue, contentColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                onClick = { navController
                .navigate("signup_screen")
            }) {
                Text(text = "Create new Account")
            }
        }
    }

}


@Preview
@Composable
fun PreviewLogin(){
    LoginScreen(navController = rememberNavController(), auth = FirebaseAuth.getInstance())
}