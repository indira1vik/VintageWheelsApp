package com.example.authentrial.repo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    var Acceleration: Int?= null,
    var Cylinders: Int?= null,
    var Displacement: Int?= null,
    var Horsepower:  Int?= null,
    var Miles_per_Gallon:  Int?= null,
    val Name: String?= null,
    var Origin: String?= null,
    var Weight_in_lbs:  Int?= null,
    var Year: String?= null
): Parcelable

sealed class DataState{
    class Success(val data: MutableList<Car>):DataState()
    class Failure(val message: String):DataState()
    object Loading:DataState()
    object Empty:DataState()
}
