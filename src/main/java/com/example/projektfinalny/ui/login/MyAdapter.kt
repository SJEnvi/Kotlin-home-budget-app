package com.example.projektfinalny.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projektfinalny.R
import com.example.projektfinalny.data.model.Transaction

//it takes in list of Transaction objects
class MyAdapter(private val transactionsList : ArrayList<Transaction>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = transactionsList[position]
        holder.tvtitle.text = currentItem.title
        holder.tvamount.text = currentItem.amount.toString()
        holder.tvDate.text = currentItem.date

    }

    //returns list size - number of transactions
    override fun getItemCount(): Int {
        return transactionsList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val tvtitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvamount : TextView = itemView.findViewById(R.id.tvAmount)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
    }
}