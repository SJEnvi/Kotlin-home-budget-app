package com.example.projektfinalny

//noinspection SuspiciousImport

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.charts.Pie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AnalyzeFragment : Fragment() {

    private val itemsOverall = arrayOf("Wydatki", "Zarobki")
    private val items = arrayOf("Rachunki", "Rozrywka i wypoczynek", "Wydatki bierzace", "Zdrowie")
    private lateinit var db: FirebaseFirestore
    private lateinit var loggedInUser: String
    private lateinit var transactionsCollection: CollectionReference
    private lateinit var anyChartView1: AnyChartView
    private lateinit var anyChartView: AnyChartView
    private lateinit var pie: Pie
    private lateinit var bar: Cartesian

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_analyze, container, false)
        db = Firebase.firestore
        loggedInUser = FirebaseAuth.getInstance().currentUser?.email!!
        transactionsCollection = db.collection("users/$loggedInUser/transactions")

//        val refreshBut = view.findViewById<Button>(R.id.chartRefBth)

//        refreshBut.setOnClickListener {refreshCharts(view)}


        val refreshBut1 = view.findViewById<Button>(R.id.chartRefBth1)

        refreshBut1.setOnClickListener {refreshCharts1(view)}

        anyChartView = view.findViewById(R.id.any_chart_view_overall)
        APIlib.getInstance().setActiveAnyChartView(anyChartView)

        pie = AnyChart.pie()
        pie.palette(arrayOf("#BE0000", "#9EDE73"))

        val data: MutableList<DataEntry> = ArrayList()
        var minus = 0.0
        var plus = 0.0
        transactionsCollection.get()
            .addOnSuccessListener { transactions ->
                for (transaction in transactions) {
                    val amount = transaction.getDouble("amount")!!
                    if (amount >= 0) {
                        plus += amount}else{minus+=amount*(-1)}
                }
                data.add(ValueDataEntry(itemsOverall[0], minus))
                data.add(ValueDataEntry(itemsOverall[1], plus))
                pie.data(data)
            }

        pie.title("Zarobki vs Wydatki")
        anyChartView.setChart(pie)



        // Inflate the layout for this fragment
        return view
    }

//    private fun refreshCharts(view: View){
//
//
//    }


    private fun refreshCharts1(view: View){

        anyChartView1 = view.findViewById(R.id.any_chart_view)
        APIlib.getInstance().setActiveAnyChartView(anyChartView1)

        bar = AnyChart.bar()
//        bar.palette(arrayOf("#5FAD56", "#F2C14E", "#F78154", "#4D9078", "#B4436C"))
        bar.title("Poszczególne kategorie wydatków")
        val data1: MutableList<DataEntry> = ArrayList()
        for (item in items) {
            var sum = 0.0
            transactionsCollection.whereEqualTo("category", item).get()
                .addOnSuccessListener { transactions ->
                    for (transaction in transactions) {
                        sum += transaction.getDouble("amount")!!
                    }
                    data1.add(ValueDataEntry(item, sum))
                    bar.data(data1)

                }
        }
        anyChartView1.setChart(bar)
    }
}







