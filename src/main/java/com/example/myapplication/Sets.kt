//package com.example.myapplication
//
//fun main(){
//    //sets ignore duplicates
//    val fruits = setOf("Orange", "Apple", "Grape", "Apple")
//    println(fruits.size)
//    println(fruits.toSortedSet())
//
//    val newDruits = fruits.toMutableList()
//    newDruits.add("water melon")
//    println(newDruits)
//
//    val daysOfTheWeek = mapOf(3 to "Monday", 2 to "Tuesday", 1 to "Wednesday")
//    //in this case we use key instead of index
//    println(daysOfTheWeek[3])
//
//    for (key in daysOfTheWeek.keys){
//        println("The key is $key, and it contains ${daysOfTheWeek[key]}")
//    }
//
//    val fruitsMap = mapOf( "Favourite" to Fruit("Apple", 2.5), "Worst" to Fruit("Duran", 200.0))
//    println(fruitsMap["Worst"]?.name)
//
//    //enabling adding to the map... Making it mutable
//    val newDaysOfWeek = daysOfTheWeek.toMutableMap()
//    newDaysOfWeek[4] = "Thursday"
//    println(newDaysOfWeek)
//}
//
//data class Fruit(val name: String, val price: Double)