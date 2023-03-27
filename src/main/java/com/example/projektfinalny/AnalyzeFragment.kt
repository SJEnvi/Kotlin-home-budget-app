package com.example.projektfinalny

//noinspection SuspiciousImport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AnalyzeFragment : Fragment() {

    private val FILE_NAME = "transactions.txt"
    private val itemsOverall = arrayOf("Wydatki", "Zarobki")
    private val items = arrayOf("Rachunki", "Rozrywka i wypoczynek", "Wydatki bierzace", "Zdrowie")
    lateinit var db: FirebaseFirestore
    lateinit var loggedInUser: String
    lateinit var transactionsCollection: CollectionReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_analyze, container, false)
        db = Firebase.firestore
        loggedInUser = FirebaseAuth.getInstance().currentUser?.email!!
        transactionsCollection = db.collection("users/$loggedInUser/transactions")


//        val bar = AnyChart.bar()
////
//        val data: MutableList<DataEntry> = ArrayList()
//        for (item in items){
//            if (getAmount(item) != 0.0) {data.add(ValueDataEntry(item, getAmount(item)))}
//        }
//
//        bar.xAxis(true)
//            .title("Categories")
//
//        bar.yAxis(true)
//            .title("Amount")
//
//        bar.data(data)
//        bar.width("100%")
//        bar.height("100%")
//        val anyChartView = view.findViewById<AnyChartView>(R.id.any_chart_view)
//        anyChartView.setChart(bar)

        val anyChartView: AnyChartView = view.findViewById(R.id.any_chart_view_overall)
        APIlib.getInstance().setActiveAnyChartView(anyChartView)

        val pie = AnyChart.pie()
        pie.palette(arrayOf("#BE0000", "#9EDE73"))

        val data: MutableList<DataEntry> = ArrayList()
        for (item in itemsOverall){
            var sum = 0.0
            transactionsCollection.get()
                .addOnSuccessListener { transactions ->
                    for (transaction in transactions) {
                        var amount = transaction.getDouble("amount")!!
                        if (amount >= 0) {
                            sum += amount}else{sum+=amount*(-1)}
                        }
                    }
                    data.add(ValueDataEntry(item, sum))
        }

        pie.data(data)

        pie.title("Zarobki vs Wydatki")

        anyChartView.setChart(pie)

        val anyChartView1: AnyChartView = view.findViewById(R.id.any_chart_view)

        APIlib.getInstance().setActiveAnyChartView(anyChartView1)

        val bar = AnyChart.bar()
//        bar.palette(arrayOf("#5FAD56", "#F2C14E", "#F78154", "#4D9078", "#B4436C"))
        bar.title("Poszczególne kategorie wydatków");
        val data1: MutableList<DataEntry> = ArrayList()
        for (item in items) {
            var sum = 0.0
            transactionsCollection.whereEqualTo("category", item).get()
                .addOnSuccessListener { transactions ->
                    for (transaction in transactions) {
                        sum += transaction.getDouble("amount")!!
                    }
                    data1.add(ValueDataEntry(item, sum))
                }
        }

        bar.data(data1)

        anyChartView1.setChart(bar)


        // Inflate the layout for this fragment
        return view
    }


//    private fun getAmount(categoryName: String): Double {
//
//                return@addOnSuccessListener sum
//            }
//
//        for (line in fileTextSeparated) {
//            var transactionData = line.split(",")
//            var partialAmount = transactionData[2].toDouble()
//
//            if (categoryName == "Wydatki"){
//                if (partialAmount < 0) amount += partialAmount * -1
//            }else if (categoryName == "Zarobki"){
//                if (partialAmount >= 0) amount += partialAmount
//            }
//            if (transactionData[3] == categoryName){
//                amount += if (partialAmount < 0) {
//                    partialAmount * -1
//                } else {
//                    partialAmount
//                }
//            }
//        }
//        inputStream?.close()
//        return amount
    }







