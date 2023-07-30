package com.example.hackofpi

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.hackofpi.model.User
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation

import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*


// TODO (Step 6: Implement the NavigationView.OnNavigationItemSelectedListener and add the implement members of it.)
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener
{

    private lateinit var drawer_layout : DrawerLayout
    private lateinit var nav_view : NavigationView

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)

        // TODO (Step 4: Call the setup action bar function here.)
        // START
        setupActionBar()
        // END

        drawer_layout = findViewById(R.id.drawer_layout)

        // TODO (Step 8: Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.)
        // START
        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        nav_view = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)

        // This is used to get the file from the assets folder and set it to the title textView.
        val main_page_heading :TextView = findViewById(R.id.main_page_heading)

        val typeFace: Typeface = Typeface.createFromAsset(assets , "carbon_bl.ttf")
        main_page_heading.typeface = typeFace
        var lat = 0.0
        var lng = 0.0
        var latLng: GeoLocation = GeoLocation(lat,lng)
        var size = 0;
        var flag = false
        var imagePaths : ArrayList<Uri> = ArrayList<Uri>()
        var i: Intent = Intent(this, FileComplaintActivity::class.java)
        var hash = GeoFireUtils.getGeoHashForLocation(latLng).toString()
        var name = ""
        fileComplaintButton.setOnClickListener {
            i.putExtra("hash", hash)
            i.putExtra("flag", flag)
            i.putExtra("size",size)
            i.putExtra("imagePathList", imagePaths)
            i.putExtra("lat",lat)
            i.putExtra("lng",lng)
            i.putExtra("name", name)
            startActivity(i)
        }
        // END


        // Get the current logged in user details.
        FirestoreClass().loadUserData(this@MainActivity)

        viewMyComplaintsButton.setOnClickListener {
            startActivity(Intent(this,ViewComplaintsActivity::class.java))
        }
    }

    // TODO (Step 5: Add a onBackPressed function and check if the navigation drawer is open or closed.)
    // START
    override fun onBackPressed()
    {
        drawer_layout = findViewById(R.id.drawer_layout)
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            // A double back press function is added in Base Activity.
            doubleBackToExit()
        }
        finishAffinity()
    }
    // END


    // TODO (Step 7: Implement members of NavigationView.OnNavigationItemSelectedListener.)
    // START
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean
    {
        // TODO (Step 9: Add the click events of navigation menu items.)
        // START
        when (menuItem.itemId) {
            R.id.nav_my_profile -> {

                //Toast.makeText(this@MainActivity, "My Profile", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
            }

            R.id.nav_sign_out -> {
                // Here sign outs the user from firebase in this device.
                //FirebaseAuth.getInstance().signOut()

                // Send the user to the intro screen of the application.
                //val intent = Intent(this, IntroActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }
            R.id.nav_help ->{
                startActivity(Intent(this, HelpActivity::class.java))
            }
            R.id.nav_about_us ->{
                startActivity(Intent(this, AboutUsActivity::class.java))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        // END
        return true
    }
    // END


    // TODO (Step 4: Add the onActivityResult function and check the result of the activity for which we expect the result.)
    // START
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
            && requestCode == MY_PROFILE_REQUEST_CODE
        ) {
            // Get the user updated details.
            FirestoreClass().loadUserData(this@MainActivity)
        } else {
            Log.e("Cancelled", "Cancelled")
        }
    }
    // END


    // TODO (Step 1: Create a function to setup action bar.)
    // START
    /**
     * A function to setup action bar
     */
    private fun setupActionBar()
    {
        val toolbar_main_activity : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main_activity)
        setSupportActionBar(toolbar_main_activity)
        toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        // TODO (Step 3: Add click event for navigation in the action bar and call the toggleDrawer function.)
        // START
        toolbar_main_activity.setNavigationOnClickListener {
            toggleDrawer()
        }
        // END
    }
    // END

    // TODO (Step 2: Create a function for opening and closing the Navigation Drawer.)
    // START
    /**
     * A function for opening and closing the Navigation Drawer.
     */
    private fun toggleDrawer()
    {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }
    // END


    /**
     * A function to get the current user details from firebase.
     */
    fun updateNavigationUserDetails(user: User) {
        // The instance of the header view of the navigation view.
        val headerView = nav_view.getHeaderView(0)

        // The instance of the user image of the navigation view.
        val navUserImage = headerView.findViewById<ImageView>(R.id.iv_user_image)

        // Load the user image in the ImageView.
        Glide
            .with(this@MainActivity)
            .load(user.image) // URL of the image
            .centerCrop() // Scale type of the image.
            .placeholder(R.drawable.ic_user_place_holder) // A default place holder
            .into(navUserImage) // the view in which the image will be loaded.

        // The instance of the user name TextView of the navigation view.
        val navUsername = headerView.findViewById<TextView>(R.id.tv_username)
        // Set the user name
        navUsername.text = user.name
    }


    // TODO (Step 1: Create a companion object and a constant variable for My profile Screen result.)
    // START
    /**
     * A companion object to declare the constants.
     */
    companion object {
        //A unique code for starting the activity for result
        const val MY_PROFILE_REQUEST_CODE: Int = 11
    }
    // END

}

