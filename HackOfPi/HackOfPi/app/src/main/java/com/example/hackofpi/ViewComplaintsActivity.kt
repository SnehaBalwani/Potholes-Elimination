package com.example.hackofpi

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackofpi.model.Complaint

import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_view_complaints.*

class ViewComplaintsActivity : AppCompatActivity() {
    companion object {

        var lat = 0.0
        var lng = 0.0
        lateinit var complaintsListAdapter: ComplaintsListAdapter
    }

    private val mFireStore = FirebaseFirestore.getInstance()
    lateinit var complaintsidList: MutableList<DocumentReference>
    var complaintsList: ArrayList<Complaint> = ArrayList<Complaint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_complaints)
        val recyclerView: RecyclerView = findViewById(R.id.complaints_list)
        val progressBar: ProgressBar = findViewById(R.id.progressBarComplaints)
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE

        mFireStore.collection("users").document(FirestoreClass().getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                if (document.get("complaintList") != null)
                    complaintsidList =
                        document.get("complaintList") as MutableList<DocumentReference>
                Log.i(TAG, "Area id list appended ${complaintsList}")
                complaintsidList.forEach() {
                    println("areaId is ${it.id}")
                    mFireStore.collection("complaints")
                        // The document id to get the Fields of user.
                        .document(it.id)
                        .get()
                        .addOnSuccessListener { document ->
//                Log.e(activity.javaClass.simpleName, document.toString())

                            // Here we have received the document snapshot which is converted into the User Data model object.

                            val name: String = document.get("name") as String
                            val id: String = document.id as String
                            val comments: String = document.get("comments") as String
                            val image1: String = document.get("image1") as String
                            val image2: String = document.get("image2") as String
                            val image3: String = document.get("image3") as String
                            val image4: String = document.get("image4") as String
                            val image5: String = document.get("image5") as String
                            var count = 5;
                            if(image5 == ""){
                                count = count - 1;
                            }
                            if(image4 == ""){
                                count = count - 1;
                            }
                            if(image3 == ""){
                                count = count - 1;
                            }
                            if(image2 == ""){
                                count = count - 1;
                            }
                            if(image1 == ""){
                                count = count - 1;
                            }

                            val latLng = document.get("latLng") as HashMap<String,Double>
                            val lat = latLng["latitude"] as Double
                            val lng = latLng["longitude"] as Double
                            val latLng1 = GeoLocation(lat,lng)

                            val hash: String = document.get("hash") as String
                            val status: Long = document.get("status") as Long
                            val complaint: Complaint = Complaint(id,
                                comments,
                                image1,
                                image2,
                                image3,
                                image4,
                                image5,
                                count ,
                                latLng1,
                                hash,
                                name,
                                status)
                            complaintsList.add(complaint)
                            complaintsListAdapter.notifyDataSetChanged()


                        }
                }

                complaintsListAdapter = ComplaintsListAdapter(complaintsList)
                complaintsListAdapter.notifyDataSetChanged()
                val layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.layoutManager = layoutManager
                recyclerView.itemAnimator = DefaultItemAnimator()
                recyclerView.adapter = complaintsListAdapter
//        FirestoreClass().geoHashing(aid)
//        FirestoreClass().getSeekersHere()
                complaintsListAdapter.notifyDataSetChanged()
                progressBar.visibility = View.INVISIBLE
                if (complaintsidList.size == 0) {
                    no_complaints.visibility = View.VISIBLE

                } else {
                    recyclerView.visibility = View.VISIBLE
                    no_complaints.visibility = View.GONE

                }

            }
    }

}



