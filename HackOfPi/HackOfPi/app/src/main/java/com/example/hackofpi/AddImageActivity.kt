package com.example.hackofpi

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import android.provider.MediaStore
import android.app.ProgressDialog
import android.net.Uri
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import com.firebase.geofire.GeoLocation
import com.google.common.io.Files.getFileExtension
import java.io.IOException
import java.util.*

class AddImageActivity : AppCompatActivity() {
    private var btnChoose: Button? = null
    private var btnUpload: Button? = null
    private var btnDone: Button? = null
    private var imageView: ImageView? = null
    lateinit var imagePaths: ArrayList<Uri>
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)
        btnChoose = findViewById<View>(R.id.btnChoose) as Button
        btnUpload = findViewById<View>(R.id.btnUpload) as Button
        imageView = findViewById<View>(R.id.imgView) as ImageView
        btnDone = findViewById<View>(R.id.btnDone) as Button
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        val i = Intent(this, FileComplaintActivity::class.java)
        val extras = intent.extras
//        val latLng = extras?.get("latLng") as LatLng
//        val latlng : LatLng = LatLng.getDefaultInstance()
        imagePaths = extras?.get("imagePathList")!! as ArrayList<Uri>
        print("imagepaaths" + imagePaths)
        var size  = extras.get("size")!! as Int
        val lat = extras.get("lat")!! as Double
        val lng = extras.get("lng")!! as Double
        val flag = extras.get("flag")!! as Boolean
        val hash = extras.get("hash")!! as String
        val latLng = GeoLocation(lat,lng)
        val name = extras.get("name") as String
//        val latLng = extras?.get("latLng") as LatLng
//        val latlng : LatLng = LatLng.getDefaultInstance()
        btnChoose!!.setOnClickListener { chooseImage() }
        btnUpload!!.setOnClickListener { uploadImage() }

        btnDone!!.setOnClickListener {
             size = imagePaths!!.size
            if (imagePaths.size == 0) {
                Toast.makeText(this@AddImageActivity, "No image added ", Toast.LENGTH_SHORT).show()
            }
            if (imagePaths.size < 5) {
                while (imagePaths.size != 5) {
                    imagePaths.add(Uri.EMPTY)
                }
            }
            i.putExtra("imagePathList", imagePaths)
            i.putExtra("size", size)
            i.putExtra("flag", flag)
            i.putExtra("hash", hash)
            i.putExtra("lat", lat)
            i.putExtra("lng", lng)
            i.putExtra("name", name)
            startActivity(i)
        }
    }

    private fun chooseImage() {
        if (imagePaths == null || imagePaths!!.size <= 5) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST)
        } else {
            Toast.makeText(this@AddImageActivity, "Can add only upto 5 images ", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun getFileExtension(uri: Uri?): String?
    {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri!!))
    }
    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref: StorageReference = FirebaseStorage.getInstance().reference.child(
                "USER_IMAGE" + System.currentTimeMillis() + "." + getFileExtension(
                    filePath
                )
            )

            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this@AddImageActivity, "Uploaded", Toast.LENGTH_SHORT).show()
                    imagePaths!!.add(filePath!!)
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@AddImageActivity, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }
}