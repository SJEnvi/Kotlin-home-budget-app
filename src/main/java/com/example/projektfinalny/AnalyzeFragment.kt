package com.example.projektfinalny

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.projektfinalny.data.model.Transaction

class AnalyzeFragment : Fragment() {

    val FILE_NAME = "transactions.txt"
    val items = arrayOf("Wpływy","Rachunki", "Rozrywka i wypoczynek", "Wydatki bierzące", "Zdrowie")
    val pieLegend = view?.findViewById<TextView>(R.id.pieLegend)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_analyze, container, false)

        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
//        val supportingList = getChartData()
        for (item in items){
            if (getAmount(item) != 0.0) data.add(ValueDataEntry(item, getAmount(item)))
        }
//        data.add(ValueDataEntry("John", 10000))
//        data.add(ValueDataEntry("Jake", 12000))
//        data.add(ValueDataEntry("Peter", 18000))

        pie.data(data)

        val anyChartView = view.findViewById<AnyChartView>(R.id.any_chart_view)
        anyChartView.setChart(pie)

        // Inflate the layout for this fragment
        return view
    }

    //TODO add function getChartData, it should return list of elements (category , amount)
//    private fun getChartData(): List<Int>{
//        val amountList: MutableList<Int> = ArrayList(0)
//        for (i in 0 .. items.size){
//            //TODO replace the below
//            // categoryList.add(i, getCatData(i))
//            amountList.add(1000)
//        }
//        return amountList
//    }
    //TODO add function that will take data from file and sum them by category


    private fun getAmount(categoryName: String): Double {
        //reading data from the file
        val inputStream = context?.openFileInput(FILE_NAME)
        val buffer = ByteArray(inputStream?.available() ?: 0)
        inputStream?.read(buffer)
        //note it's saved as single string
        val fileText = String(buffer)
        // thus we first split into list of lines
        var fileTextSeparated = fileText.split("\n")

        //then we take every line and create a Transaction object out of the data in it
        var amount = 0.0
        for (line in fileTextSeparated) {
            var transactionData = line.split(",")
            var partialAmount = transactionData[2].toDouble()
            if (transactionData[3] == categoryName){
                if (partialAmount < 0) {amount += partialAmount * -1} else {amount += partialAmount}
            }
            //finally send the data to recyclerview adapter
        }

//        var currentText = pieLegend?.text
//        pieLegend?.text = currentText.toString() + "\n$categoryName: $amount"

        return amount
    }

}