package com.example.projektfinalny.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projektfinalny.GoogleAuth
import com.example.projektfinalny.R
import com.example.projektfinalny.data.model.Transaction
import kotlinx.coroutines.withContext
import java.lang.String.format
import java.text.SimpleDateFormat

//it takes in list of Transaction objects
class MyAdapter(private val context: Context, private val transactionsList : ArrayList<Transaction>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val simpleDataFormat =  SimpleDateFormat("yyyy-MM-dd")
        val currentItem = transactionsList[position]
        holder.tvTitle.text = currentItem.title
        holder.tvAmount.text = currentItem.amount.toString()
        holder.tvDate.text = currentItem.category
        holder.tvActualDate.text = simpleDataFormat.format(currentItem.date)

        //This part is for making edit transaction window appear
        holder.itemView.setOnClickListener{
            val intent = Intent( context, UpdateActivity::class.java)
            intent.putExtra("transaction", currentItem)
            context.startActivity(intent)
        }
    }

    //returns list size - number of transactions
    override fun getItemCount(): Int {
        return transactionsList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvAmount : TextView = itemView.findViewById(R.id.tvAmount)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvActualDate : TextView = itemView.findViewById(R.id.tvSavedDate)
    }
}