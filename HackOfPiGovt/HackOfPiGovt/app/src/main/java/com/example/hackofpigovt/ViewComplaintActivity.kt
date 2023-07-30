package com.example.hackofpigovt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_view_complaint.*

class ViewComplaintActivity : AppCompatActivity() {
    private val mFireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_complaint)

        val extras = intent.extras
        val id = extras?.get("id") as String
        val ref1 = extras?.get("image1") as String
        val ref2 = extras?.get("image2") as String
        val ref3 = extras?.get("image3") as String
        val ref4 = extras?.get("image4") as String
        val ref5 = extras?.get("image5") as String
        val name = extras?.get("name") as String
        val comments = extras?.get("comments") as String
        c_name.text = name
        c_comments.text = comments


//        mFireStore.collection("complaints").document(id)
//            .get()
//            .addOnSuccessListener { doc ->
//                print("id ${doc.id}")
//                c_name.text = doc.get("name").toString()
//                c_comments.text = doc.get("comments") as String
//                val ref1 = doc.get("image1").toString()
//                val ref2 = doc.get("image2") as String
//                val ref3 = doc.get("image3") as String
//                val ref4 = doc.get("image4") as String
//                val ref5 = doc.get("image5") as String
//                print("ref1 $ref1")

                // WORKING CODE!
//                val storage = FirebaseStorage.getInstance()// Create a reference to a file from a Google Cloud Storage URI
//                val gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg")
//                GlideApp.with(context)
//                    .load(gsReference)
//                    .into(imageView)
//            }
// TODO ATTENTION. THIS CODE BLOCK IS NOT WORKING!
//                val storage = FirebaseStorage.getInstance()
//                val storageReference = Firebase.storage.reference
//                // Create a reference to a file from a Google Cloud Storage URI
////                if (ref1 != "") {
//                    print("ref1 $ref1")
//                    val gsReference1 = storage.getReferenceFromUrl(ref1)
//
//                    Glide.with(this@ViewComplaintActivity)
//                        .load(gsReference1)
//                        .centerCrop()
//                        .into(img1)
////                } else {
////                    img1.visibility = View.INVISIBLE
////                }
//                if (ref2 != "") {
////                    val gsReference2 = storage.getReferenceFromUrl(ref1)
//
//                    Glide.with(this@ViewComplaintActivity)
//                        .load(ref2)
//                        .into(img2)
//
//                } else {
//                    img2.visibility = View.INVISIBLE
//                }
//                if (ref3 != "") {
//                    val gsReference3 = storage.getReference(ref1)
//
//                    Glide.with(this@ViewComplaintActivity)
//                        .load(gsReference3)
//                        .into(img3)
//
//                } else {
//                    img3.visibility = View.INVISIBLE
//                }
//                if (ref4 != "") {
//                    val gsReference4 = storage.getReferenceFromUrl(ref1)
//
//                    Glide.with(this@ViewComplaintActivity)
//                        .load(gsReference4)
//                        .into(img4)
//                } else {
//                    img4.visibility = View.INVISIBLE
//                }
//                if (ref5 != "") {
//                    val gsReference5 = storage.getReferenceFromUrl(ref1)
//
//                    Glide.with(this@ViewComplaintActivity)
//                        .load(gsReference5)
//                        .into(img1)
//                } else {
//                    img5.visibility = View.INVISIBLE
//                }


            }

    }
