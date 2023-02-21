package com.example.projektfinalny

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projektfinalny.ui.login.MyAdapter
import com.example.projektfinalny.data.model.Transaction

class HistoryFragment : Fragment() {
    //declaring variables I will use later on - in onCreate
    lateinit var newRecyclerView: RecyclerView
    lateinit var newArrayList: ArrayList<Transaction>
    lateinit var title : Array<String>
    lateinit var amount : Array<Double>
    lateinit var date : Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO Remove these after getTransactionData is changed
        title = arrayOf("Biedra", "Lidl", "Tesco")
        amount = arrayOf(20.0, 30.0, 11.0)
        date = arrayOf("10-2-2023", "8-1-2023", "1-1-2001")
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_history, container, false)
        //initializing recycler view
        newRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        newRecyclerView?.layoutManager = LinearLayoutManager(view?.context)
        newRecyclerView?.setHasFixedSize(true)

        //first I assign the array of Transaction objects to newArrayList to make it changeable
        newArrayList = arrayListOf<Transaction>()
        //then populate it with data
        getTransactionData()


        return view
    }

    //TODO Replace this function with the one that gets data from file
    private fun getTransactionData(){
        for(i in title.indices){
            val transaction = Transaction(title[i], amount[i], date[i])
            newArrayList.add(transaction)
        }

        //finally send the data to recyclerview adapter
        newRecyclerView.adapter = MyAdapter(newArrayList)
    }
}