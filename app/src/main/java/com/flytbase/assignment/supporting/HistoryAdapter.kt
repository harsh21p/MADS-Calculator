package com.flytbase.assignment.supporting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.flytbase.assignment.R
import com.flytbase.assignment.activity.History


internal class HistoryAdapter (private var List: List<DataModel>, private val listener: History) :
    RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    internal open inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position!= RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_model_view, parent, false)
        return View1ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        (holder as View1ViewHolder).bind(position)
    }
    override fun getItemCount(): Int {
        return List.size
    }
    private inner class View1ViewHolder(itemView: View) :
        MyViewHolder(itemView) {
        var result: TextView = itemView.findViewById(R.id.result)
        var input: TextView = itemView.findViewById(R.id.input)

        fun bind(position: Int) {
            val recyclerViewModel = List[position]
            input.text = recyclerViewModel.input
            result.text = recyclerViewModel.result.toString()

        }

    }
}
