package com.example.projektfinalny

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry

class AnalyzeFragment : Fragment() {

    val items = arrayOf("Jedzenie", "Rachunki", "Inne", "Duperele", "Trele morele")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_analyze, container, false)

        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
        val supportingList = getChartData()
        for (i in items.indices){
            data.add(ValueDataEntry(items[i], supportingList[i]))
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
    private fun getChartData(): List<Int>{
        val categoryList: MutableList<Int> = ArrayList(0)
        for (i in 0 .. items.size){
            //TODO replace the below
            // categoryList.add(i, getCatData(i))
            categoryList.add(1000)
        }
        print(categoryList)
        return categoryList
    }
    //TODO add function that will take data from file and sum them by category
}