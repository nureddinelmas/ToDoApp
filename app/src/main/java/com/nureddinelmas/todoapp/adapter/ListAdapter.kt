package com.nureddinelmas.todoapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nureddinelmas.todoapp.R
import com.nureddinelmas.todoapp.activities.fragment.ListFragmentDirections
import com.nureddinelmas.todoapp.model.Priority
import com.nureddinelmas.todoapp.model.ToDo
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.layout_row.view.*


class ListAdapter(): RecyclerView.Adapter<ListAdapter.MyHolder>() {

    var toDoList = emptyList<ToDo>()

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_row, parent, false)
        return MyHolder(view)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
       holder.itemView.title_row.text = toDoList[position].title
        holder.itemView.description_row.text = toDoList[position].description
        holder.itemView.timeDate_row.text = toDoList[position].reminderDate.toString()

        when(toDoList[position].period){
            Priority.DAILY -> {
                holder.itemView.spinner_row.text = toDoList[position].period.name
                holder.itemView.spinner_row.setTextColor(R.color.red)
            }

            Priority.WEEKLY -> holder.itemView.spinner_row.text = toDoList[position].period.name
        }


        holder.itemView.background_row.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoList[position])

            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }


    fun setData(toDoData : List<ToDo>){
        this.toDoList = toDoData
        notifyDataSetChanged()
    }
}