package com.example.hackofpi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        setUpActionBar()

    }


    private fun setUpActionBar()
    {
        val toolbar_about_us_activity : Toolbar
        toolbar_about_us_activity = findViewById(R.id.toolbar_about_us_activity)

        setSupportActionBar(toolbar_about_us_activity)

        val actionBar = supportActionBar

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.about_us_title)
        }

        toolbar_about_us_activity.setNavigationOnClickListener{
            onBackPressed()
        }

    }
}