package com.example.hackofpi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.example.hackofpi.model.User
import kotlinx.android.synthetic.main.activity_file_complaint.*
import com.firebase.geofire.GeoLocation

class FileComplaintActivity : BaseActivity() {

    // A global variable for user details.
    private lateinit var mUserDetails: User
    // A global variable for a user profile image URL
    private var mSelectedImageFileUri: Uri? = null

    private var mProfileImageURL: String = ""
    companion object{
        private const val READ_STORAGE_PERMISSION_CODE = 1

        private const val PICK_IMAGE_REQUEST_CODE = 2

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_complaint)
//        var lat = 0.0
//        var lng = 0.0
//        var latLng: GeoLocation = GeoLocation(lat,lng)
//        var size = 0;
//        var flag = false
//        var imagePaths : ArrayList<Uri> = ArrayList()
        val i_image : Intent = Intent(this,AddImageActivity::class.java)
        val i_location : Intent = Intent(this, PlacesAutocompleteActivity::class.java)
//        var hash = GeoFireUtils.getGeoHashForLocation(latLng).toString()
//

        val extras = intent.extras
//        val latLng = extras?.get("latLng") as LatLng
//        val latlng : LatLng = LatLng.getDefaultInstance()
        val imagePaths: ArrayList<Uri> = extras?.get("imagePathList")!! as ArrayList<Uri>
        print("imagepaaths" + imagePaths)
        val size  = extras.get("size")!! as Int
        val lat = extras.get("lat")!! as Double
        val lng = extras.get("lng")!! as Double
        val flag = extras.get("flag")!! as Boolean
        val hash = extras.get("hash")!! as String
        val name = extras.get("name")!! as String
        val latLng = GeoLocation(lat,lng)

        btn_add_image.setOnClickListener {
            i_image.putExtra("hash", hash)
            i_image.putExtra("flag", flag)
            i_image.putExtra("size",size)
            i_image.putExtra("imagePathList", imagePaths)
            i_image.putExtra("lat",lat)
            i_image.putExtra("lng",lng)
            i_image.putExtra("name",name)
            startActivity(i_image)
        }

        btn_add_location.setOnClickListener {
            i_location.putExtra("hash", hash)
            i_location.putExtra("flag", flag)
            i_location.putExtra("size",size)
            i_location.putExtra("imagePathList", imagePaths)
            i_location.putExtra("lat",lat)
            i_location.putExtra("lng",lng)
            i_location.putExtra("name",name)
            startActivity(i_location)
        }
//        btn_add_image.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                showImageChooser()
//            } else {
//                /*Requests permissions to be granted to this application. These permissions
//                 must be requested in your manifest, they should not be granted to your app,
//                 and they should have protection level*/
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    READ_STORAGE_PERMISSION_CODE
//                )
//            }
//        }

        btn_file_complaint.setOnClickListener {

          updateDetails(imagePaths,latLng,size,flag,hash,name)


        }

    }
    private fun showImageChooser()
    {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }
    private fun updateDetails(
        imagePaths: ArrayList<Uri>,
        latLng: GeoLocation,
        size: Int,
        flag: Boolean,
        hash: String,
        name: String
    ) {

        val comments: String = et_comments.text.toString().trim { it <= ' ' }




        val image1 = imagePaths[0]
        val image2 = imagePaths[1]
        val image3 = imagePaths[2]
        val image4 = imagePaths[3]
        val image5 = imagePaths[4]



        if (validateForm(size, latLng, comments,flag,hash)) {
            FirestoreClass().setComplaint(image1.toString(), image2.toString(), image3.toString(), image4.toString(), image5.toString(), latLng, comments,hash,name)

            startActivity(Intent(this, MainActivity::class.java))


//        startActivity(Intent(this@AddDetailsActivity, DonorActivity::class.java))
        }


    }



    private fun validateForm(

        size: Int,
        latLng: GeoLocation,
        comments: String,
        flag: Boolean,
        hash: String
    ): Boolean {

        return when {

            (flag == false) -> {
                showErrorSnackBar("Please enter the location")
                false
            }
            (size == 0) ->{
                showErrorSnackBar("Please add at least one image")
                false
            }


            else -> {
                true
            }

        }

    }


}