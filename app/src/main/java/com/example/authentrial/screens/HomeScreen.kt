package com.example.authentrial.screens

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.authentrial.R
import com.example.authentrial.repo.Car
import com.example.authentrial.repo.DataState
import com.example.authentrial.repo.MainView
import com.google.firebase.auth.FirebaseAuth

@Composable
fun searchFieldWithIcons() : String {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "SearchIcon") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        label = { Text(text = "Search") },
        placeholder = { Text(text = "Search here") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Black,
            leadingIconColor = Color.Black,
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        ),
        trailingIcon = { IconButton(onClick = { text = "" }) {
            Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close") }
        }
    )
    return text
}


@Composable
fun HomeScreen(navController : NavController, auth: FirebaseAuth,viewModel: MainView) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "bg",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VintageWheels",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                FloatingActionButton(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        navController.navigate("profile_screen")
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Person, contentDescription = "person")
                }
            }
            Spacer(modifier = Modifier.padding(12.dp))
            SetData(viewModel,navController)
        }
    }
}

@Composable
fun SetData(viewModel: MainView, navController: NavController) {
    when (val result = viewModel.response.value){
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center){
                CircularProgressIndicator()
            }
        }
        is DataState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center){
                Text(text = result.message, style = TextStyle(fontSize = 24.sp))
            }
        }
        is DataState.Success -> {
            SearchList(result.data)
            DisplayData(result.data)
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center){
                Text(text = "Error in Fetching Data", style = TextStyle(fontSize = 24.sp))
            }
        }
    }
}

@Composable
fun DisplayData(cars: MutableList<Car>) {
    LazyColumn(
        modifier = Modifier.padding(0.dp,24.dp)
    ){
        items(cars){item->
            CardItem(car = item)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}


@Composable
fun CardItem(car: Car) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                text = car.Name!!.capitalize(),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "Horsepower : ${car.Horsepower.toString()}")
            Text(text = "Mileage : ${car.Miles_per_Gallon.toString()}")
            Text(text = "Acceleration : ${car.Acceleration.toString()}")
            Text(text = "Origin : ${car.Origin!!}")
        }
    }
}


@Composable
fun SearchList(cars: MutableList<Car>){
    var toSearch by remember { mutableStateOf("") }
    var emptySearch by remember { mutableStateOf(false) }
    val resultList: MutableList<Car> = ArrayList()
    val value = searchFieldWithIcons()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 12.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            modifier = Modifier.width(120.dp),
            onClick = {toSearch = value
            emptySearch = false}) {
            Text(text = "Search")
        }
        OutlinedButton(
            modifier = Modifier.width(120.dp),
            onClick = {
            resultList.clear()
            emptySearch = true
        }) {
            Text(text = "Clear")
        }
    }
    for (item in cars){
        if (toSearch == item.Name) {
            resultList.add(item)
        }
    }
    if (!emptySearch){
        DisplayData(cars = resultList)
    }
}