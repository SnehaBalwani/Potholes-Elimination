package com.example.hackofpigovt

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hackofpi.model.Complaint


class ComplaintsListAdapter(private var complaintsList: MutableList<Complaint>) :
    RecyclerView.Adapter<ComplaintsListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name : TextView = itemView.findViewById(R.id.t_name)
        var status: TextView = itemView.findViewById(R.id.t_status)
        var btnViewComplaint : Button= itemView.findViewById(R.id.btnViewComplaint)
        var btnSetStatus : Button = itemView.findViewById(R.id.btnSetStatus)


        init {

            btnSetStatus.setOnClickListener {
                val i : Intent = Intent(it.context, SetStatusActivity::class.java)
                val complaint = complaintsList[position]

                i.putExtra("id",complaint.id)
                it.context.startActivity(i)
            }
            btnViewComplaint.setOnClickListener {
                val i : Intent = Intent(it.context, ViewComplaintActivity::class.java)
                val complaint = complaintsList[position]
                i.putExtra("name", complaint.name)
                i.putExtra("comments", complaint.comments)
                i.putExtra("image1", complaint.image1)
                i.putExtra("image2", complaint.image2)
                i.putExtra("image3", complaint.image3)
                i.putExtra("image4", complaint.image4)
                i.putExtra("image5", complaint.image5)


                i.putExtra("id",complaint.id)
                it.context.startActivity(i)
            }

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
        if(complaint.status.toInt() == 0){
            holder.status.text = "Complaint filed"

        }
        else if(complaint.status.toInt() == 1){
            holder.status.text = "Under process"

        }
        if(complaint.status.toInt() == 2){
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
