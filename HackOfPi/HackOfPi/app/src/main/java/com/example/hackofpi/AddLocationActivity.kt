package com.example.hackofpi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_location.*
import java.util.ArrayList

class AddLocationActivity : AppCompatActivity() {

    var temp = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        val i = Intent(this, PlacesAutocompleteActivity::class.java)
        val extras = intent.extras
//        val latLng = extras?.get("latLng") as LatLng
//        val latlng : LatLng = LatLng.getDefaultInstance()
        val imagePaths: ArrayList<Uri> = extras?.get("imagePathList") as ArrayList<Uri>
        print("imagepaaths" + imagePaths)
        val size : Int = extras?.get("size") as Int
        val lat : Double = extras?.get("lat") as Double
        val lng : Double = extras?.get("lng") as Double
        val flag : Boolean = extras?.get("flag") as Boolean
        val hash : String = extras?.get("hash") as String
        val name: String = extras.get("name") as String
        btn_address.setOnClickListener {
            i.putExtra("hash", hash)
            i.putExtra("flag", flag)
            i.putExtra("size",size)
            i.putExtra("imagePathList", imagePaths)
            i.putExtra("lat",lat)
            i.putExtra("lng",lng)
            i.putExtra("name",name)

            startActivity(i)
        }
        btn_current.setOnClickListener {

        }

//        radioGroup = findViewById(R.id.radioGroup)
//        button = findViewById(R.id.btnGo)
//        button.setOnClickListener {
//            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
//            radioButton = findViewById(intSelectButton)
//            if(intSelectButton == 0){
//                //current location
//                temp = 0
//            }
//            else if(intSelectButton == 1){
//
//                temp = 1
////                startActivity(Intent(this,PlacesAutocompleteActivity::class.java))
//
//                //places autocomplete
//            }
//            Toast.makeText(baseContext,temp, Toast.LENGTH_SHORT).show()
//
//
//            if(temp == 0){
//
//            }
//            else if(temp == 1){
//                startActivity(Intent(this,PlacesAutocompleteActivity::class.java))
//            }
//        }
//
//    }

    }
}