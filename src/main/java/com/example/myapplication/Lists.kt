//package com.example.myapplication
//
//import kotlin.reflect.typeOf
//
//fun main(){
//
//    val months = listOf("January", "Feb", "March")
//
//    val anyTypes = listOf(1, 2, 3, "elo", false)
//
//    print(anyTypes.size)
//    print(months[2])
//
//    for (month in months){
//        println(month)
//    }
//
//    val additionalMonths = months.toMutableList()
//    val newMonths = arrayOf("April", "May", "June")
//    additionalMonths.addAll(newMonths)
//    additionalMonths.add("July")
//    months[0]
//
//    val days = mutableListOf<String>("Mon", "tue", "wed")
//    days.add("thu")
//    println(days)
//    days.removeAt(0)
//    println(days)
//    val removeList = mutableListOf<String>("tue", "wed")
//    //days.removeAll(removeList)
//    days.removeAll(days)
//}