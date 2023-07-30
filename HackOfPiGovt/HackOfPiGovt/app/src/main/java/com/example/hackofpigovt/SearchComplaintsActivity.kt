package com.example.hackofpigovt

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackofpi.model.Complaint

import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.*
import com.firebase.geofire.GeoFireUtils

import com.google.firebase.firestore.DocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import com.google.android.gms.tasks.Tasks

import com.firebase.geofire.GeoQueryBounds
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_search_complaints.*


class SearchComplaintsActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_search_complaints)
        val recyclerView: RecyclerView = findViewById(R.id.complaints_list)
        val progressBar: ProgressBar = findViewById(R.id.progressBarComplaints)
        complaintsListAdapter = ComplaintsListAdapter(complaintsList)
        complaintsListAdapter.notifyDataSetChanged()
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = complaintsListAdapter
//        FirestoreClass().geoHashing(aid)
//        FirestoreClass().getSeekersHere()
        complaintsListAdapter.notifyDataSetChanged()
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE

val  extras = intent.extras
        if (extras != null) {
            lat = extras.get("lat") as Double
            lng = extras.get("lng") as Double
        }

// Collect all the query results together into a single list

// Collect all the query results together into a single list
       print("Latlng bahar $lat $lng")
        val center = GeoLocation(lat, lng)
        val dist = 2
        val radiusInM = (dist * 1000).toDouble()

// Each item in 'bounds' represents a startAt/endAt pair. We have to issue
// a separate query for each pair. There can be up to 9 pairs of bounds
// depending on overlap, but in most cases there are 4.

// Each item in 'bounds' represents a startAt/endAt pair. We have to issue
// a separate query for each pair. There can be up to 9 pairs of bounds
// depending on overlap, but in most cases there are 4.
        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
        val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()
        for (b in bounds) {
            val q: Query = mFireStore.collection("complaints")
                .orderBy("hash")
                .startAt(b.startHash)
                .endAt(b.endHash)
            tasks.add(q.get())
        }

// Collect all the query results together into a single list

// Collect all the query results together into a single list
        Tasks.whenAllComplete(tasks)
            .addOnSuccessListener {
                val matchingDocs: MutableList<DocumentSnapshot> = ArrayList()
                for (task in tasks) {
                    val snap: QuerySnapshot? = task.getResult()

                    if (snap != null) {
                        for (doc in snap.documents) {
//                            val lat = doc.get("Latitude") as Double
//                            val lng = doc.get("Longitude") as Double

                            // We have to filter out a few false positives due to GeoHash
                            // accuracy, but most will match
                            val docLocation = GeoLocation(lat, lng)
                            print("latlng $lat $lng")
                            val distanceInM =
                                GeoFireUtils.getDistanceBetween(docLocation, center)
                            if (distanceInM <= radiusInM) {
                                print("andar aaya hai")
                                matchingDocs.add(doc)
                                val id = doc.id
                                print("doc id $id")
                                MainActivity.idListLocationwise.add(id)

                                print(doc.data)




                                println("koi toh doc milaa")


                                val name: String = doc.get("name").toString()
                                val comments: String = doc.get("comments") as String
                                val image1: String = doc.get("image1") as String
                                val image2: String = doc.get("image2") as String
                                val image3: String = doc.get("image3") as String
                                val image4: String = doc.get("image4") as String
                                val image5: String = doc.get("image5") as String
                                var count = 5;
                                if (image5 == "") {
                                    count = count - 1;
                                }
                                if (image4 == "") {
                                    count = count - 1;
                                }
                                if (image3 == "") {
                                    count = count - 1;
                                }
                                if (image2 == "") {
                                    count = count - 1;
                                }
                                if (image1 == "") {
                                    count = count - 1;
                                }

                                val latLng = doc.get("latLng") as HashMap<String, Double>
                                val lat = latLng["latitude"] as Double
                                val lng = latLng["longitude"] as Double
                                val latLng1 = GeoLocation(lat, lng)

                                val hash: String = doc.get("hash") as String
                                var status: Long = 0
                                if(doc.get("status").toString() != "null")
                                    status = doc.get("status").toString().toLong()
                                val complaint: Complaint = Complaint(id,
                                    comments,
                                    image1,
                                    image2,
                                    image3,
                                    image4,
                                    image5,
                                    count,
                                    latLng1,
                                    hash,
                                    name,
                                    status)
                                print("complaint hai $complaint")
                                complaintsList.add(complaint)
                                complaintsListAdapter.notifyDataSetChanged()



                                if (doc.id != null)
                                    Log.d(TAG, "${doc.id} => ${doc.data}")

                            }
                        }
                    }
                }

                println("saare docs dekh liye")

                complaintsListAdapter.notifyDataSetChanged()

            }
            .addOnCompleteListener {
                complaintsListAdapter.notifyDataSetChanged()
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                no_complaints.visibility = View.INVISIBLE

                if (complaintsList.isEmpty()) {
                    no_complaints.visibility = View.VISIBLE
                }

                if (complaintsList.size == 0) {
                    no_complaints.visibility = View.VISIBLE

                } else {
                    recyclerView.visibility = View.VISIBLE
                    no_complaints.visibility = View.INVISIBLE

                }


            }


            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }








    }


    private fun startactivitywithoutload() {
        Handler().postDelayed({
            val i = Intent(this,
                SearchComplaintsActivity::class.java)

            startActivity(i)
        }, 0)
    }


}


