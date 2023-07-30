package com.example.hackofpi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.hackofpi.model.Complaint


class ComplaintsListAdapter(private var complaintsList: MutableList<Complaint>) :
    RecyclerView.Adapter<ComplaintsListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name : TextView = itemView.findViewById(R.id.t_name)
        var status: TextView = itemView.findViewById(R.id.t_status)


        init {


        }


    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.complaint_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val complaint = complaintsList[position]
        holder.name.text = complaint.name
        if(complaint.status == 0){
            holder.status.text = "Complaint filed"

        }
        else if(complaint.status == 1){
            holder.status.text = "Under process"

        }
        if(complaint.status == 2){
            holder.status.text = "Resolved"
        }
    }
    override fun getItemCount(): Int {
        return complaintsList.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}
