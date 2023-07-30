package com.example.hackofpi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class AboutAppActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        setUpActionBar()

    }


    private fun setUpActionBar()
    {
        val toolbar_help_activity : Toolbar
        toolbar_help_activity = findViewById(R.id.toolbar_help_activity)

        setSupportActionBar(toolbar_help_activity)

        val actionBar = supportActionBar

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_help_activity.setNavigationOnClickListener{
            onBackPressed()
        }

    }
}