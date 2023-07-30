package com.example.hackofpigovt

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.collection.ArraySet
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        var flag = false
        var lat: Double = 0.0
        var lng : Double = 0.0
        var address = ""
        val idListLocationwise: MutableSet<String> = ArraySet()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = "AIzaSyDxnfwlaBfVrK0CCCDXEySWmdNRkSgWfRU"
        Places.initialize(applicationContext, apiKey)

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)


        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                val latLng = place.latLng
                address = place.name.toString()
                if (latLng != null) {
                    lat = latLng.latitude
                }
                if (latLng != null) {
                    lng = latLng.longitude
                }
                if (latLng != null) {
                    println(latLng.latitude)
                    println(latLng.longitude)

                }

                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.latLng} ")
            }

            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
//                i.putExtra("lat",lat)
//                i.putExtra("lng",lng)
//                startActivity(i)
//
            }


        })


        btn_search.setOnClickListener {
            val i = Intent(this, SearchComplaintsActivity::class.java)
            i.putExtra("lat",lat)
            i.putExtra("lng",lng)
        startActivity(i)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val AUTOCOMPLETE_REQUEST_CODE = 1
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i(TAG, "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage.toString())
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}