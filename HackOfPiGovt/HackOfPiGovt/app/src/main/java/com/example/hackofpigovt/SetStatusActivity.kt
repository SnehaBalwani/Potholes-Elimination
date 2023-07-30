package com.example.hackofpigovt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_set_status.*

class SetStatusActivity : AppCompatActivity() {
    private val mFireStore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_status)

        val extras = intent.extras
        val id = extras?.get("id") as String
        btn_0.setOnClickListener {
            setStatus(id,0)
            Toast.makeText(this,"Set as Filed",Toast.LENGTH_SHORT)
            startActivity(Intent(this,MainActivity::class.java))
        }
        btn_1.setOnClickListener {
            setStatus(id,1)
            Toast.makeText(this,"Set as Under Process",Toast.LENGTH_SHORT)
            startActivity(Intent(this,MainActivity::class.java))


        }
        btn_2.setOnClickListener {
            setStatus(id,2)
            Toast.makeText(this,"Set as Resolved",Toast.LENGTH_SHORT)
            startActivity(Intent(this,MainActivity::class.java))


        }

    }

    private fun setStatus(id: String, i: Long) {
        val map = hashMapOf<String,Any>(
            "status" to i
        )
        mFireStore.collection("complaints")
            .document(id)
            .update(map)

    }
}