package com.example.projektfinalny.data.model

import java.util.Date


//data class that will store a particular transactions
data class Transaction(var id : String, var title : String, var amount : Double, var category : String, var date: Date) {

}