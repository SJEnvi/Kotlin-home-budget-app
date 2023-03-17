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


class AnalyzeFragment : Fragment() {

    private val FILE_NAME = "transactions.txt"
    private val itemsOverall = arrayOf("Wydatki", "Zarobki")
    private val items = arrayOf("Rachunki", "Rozrywka i wypoczynek", "Wydatki bierzace", "Zdrowie")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_analyze, container, false)


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
            if (getAmount(item) != 0.0) data.add(ValueDataEntry(item, getAmount(item)))
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
        for (item in items){
            if (getAmount(item) != 0.0) data1.add(ValueDataEntry(item, getAmount(item)))
        }

        bar.data(data1)

        anyChartView1.setChart(bar)


        // Inflate the layout for this fragment
        return view
    }


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

            if (categoryName == "Wydatki"){
                if (partialAmount < 0) amount += partialAmount * -1
            }else if (categoryName == "Zarobki"){
                if (partialAmount >= 0) amount += partialAmount
            }
            if (transactionData[3] == categoryName){
                amount += if (partialAmount < 0) {
                    partialAmount * -1
                } else {
                    partialAmount
                }
            }
        }
        inputStream?.close()
        return amount
    }
}







