package com.example.hackofpi

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.google.type.LatLng
import java.util.ArrayList

class PlacesAutocompleteActivity : AppCompatActivity() {
   lateinit var i : Intent
    lateinit var latLng : com.google.android.gms.maps.model.LatLng
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_autocomplete)
        i = Intent(this, FileComplaintActivity::class.java)
        val extras = intent.extras
//        val latLng = extras?.get("latLng") as LatLng
//        val latlng : LatLng = LatLng.getDefaultInstance()
        val imagePaths: ArrayList<Uri> = extras?.get("imagePathList") as ArrayList<Uri>
        print("imagepaaths" + imagePaths)
        val size : Int = extras?.get("size") as Int
        var lat : Double = extras?.get("lat") as Double
        var lng : Double = extras?.get("lng") as Double
        var flag : Boolean = extras?.get("flag") as Boolean
        var hash : String = extras?.get("hash") as String
        var name : String = extras?.get("name" ) as String
        val apiKey = "AIzaSyDxnfwlaBfVrK0CCCDXEySWmdNRkSgWfRU"
        Places.initialize(applicationContext, apiKey)

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)


        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                latLng = place.latLng!!



                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.latLng} ")
//                FileComplaintActivity.latLng = place.latLng as LatLng
//                i.putExtra("latLng", latLng)
                lat = latLng.latitude
                lng = latLng.longitude
                name = place.name.toString()
                hash = GeoFireUtils.getGeoHashForLocation(GeoLocation(lat,lng))
                flag = true


            }

            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
                TODO("Not yet implemented")
            }


        })
        val btn_continue: Button = findViewById(R.id.btn_continue)
        btn_continue.setOnClickListener {
//            updateDetails()
          if(validateForm(this::latLng.isInitialized))
          {
                i.putExtra("hash", hash)
                i.putExtra("flag", flag)
                i.putExtra("size", size)
                i.putExtra("imagePathList", imagePaths)
                i.putExtra("lat", lat)
                i.putExtra("lng", lng)
                i.putExtra("name",name)

                startActivity(i)
            }
        }
    }
var place2 = ""
//    var latlng : LatLng = LatLng.getDefaultInstance()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val AUTOCOMPLETE_REQUEST_CODE = 1
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i(TAG, "Place: ${place.name}, ${place.id}")
                        place2 = place.name.toString()
                        val latlng = place.latLng as LatLng
                        i.putExtra("lat",latLng.latitude)
                        i.putExtra("lng", latLng.longitude)
                        i.putExtra("flag", true)
                        i.putExtra("name", place.name)
                        i.putExtra("hash", GeoFireUtils.getGeoHashForLocation(GeoLocation(latLng.latitude,latLng.longitude)).toString())

//                        FileComplaintActivity.latLng = place.latLng as LatLng
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

//    private fun updateDetails() {
//
//
//
//        if (validateForm(place2)) {
//
//
//            startActivity(Intent(this,FileComplaintActivity::class.java))
//
////    place
//
//
//
////        startActivity(Intent(this@AddDetailsActivity, DonorActivity::class.java))
//        }
//
//    }

    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@PlacesAutocompleteActivity,
                R.color.snackbar_error_color
            )
        )
        snackBar.show()
    }

    fun validateForm(initialized: Boolean): Boolean {
        return when {
            (!initialized) -> {
                showErrorSnackBar("Please add location")
                false
            }


            else -> {
                true
            }

        }
    }
}