package com.example.authentrial.repo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.FirebaseDatabase.*
import com.google.firebase.database.ValueEventListener

class MainView: ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init{
        fetchDataFromFireBase()
    }

    private fun fetchDataFromFireBase() {
        val tempList = mutableListOf<Car>()
        response.value = DataState.Loading

        getInstance()
            .getReference("myData/")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(EachData in snapshot.children){
                        val carItem = EachData.getValue(Car::class.java)
                        if (carItem != null){
                            tempList.add(carItem)
                        }
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })
    }
}