package com.example.myapplication

fun main(){
    val myList = arrayListOf<Double>()
    for (i in 0..4){
        myList.add(i, i+2.9)
    }

    print(myList.sum())

}