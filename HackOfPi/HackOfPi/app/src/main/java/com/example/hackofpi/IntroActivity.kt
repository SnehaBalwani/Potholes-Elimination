package com.example.hackofpi

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class IntroActivity : AppCompatActivity()
{
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_intro)

        /**
        // This is used to hide the status bar and make the intro screen as a full screen activity.
        window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

         */


        val tv : TextView = findViewById(R.id.intro_tv_app_name)

        // This is used to get the file from the assets folder and set it to the title textView.
        val typeFace: Typeface = Typeface.createFromAsset(assets , "carbon_bl.ttf")
        tv.typeface = typeFace


        val intro_btn_sign_in : Button = findViewById(R.id.intro_btn_sign_in)
        intro_btn_sign_in.setOnClickListener{

            // Launch the sign in screen.
            startActivity(Intent(this, SignInActivity::class.java))
        }


        val intro_btn_sign_up : Button = findViewById(R.id.intro_btn_sign_up)
        intro_btn_sign_up.setOnClickListener {

            // Launch the sign up screen.
            startActivity(Intent(this@IntroActivity, SignUpActivity::class.java))
        }

    }
}