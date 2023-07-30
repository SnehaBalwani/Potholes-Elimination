package com.example.hackofpi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.hackofpi.model.Complaint
import com.example.hackofpi.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
//import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.w3c.dom.Text
import java.util.ArrayList

class SignUpActivity : BaseActivity()
{

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_sign_up)


        setUpActionBar()

        // Click event for sign-up button.
        val btn_sign_up : Button = findViewById(R.id.btn_sign_up)
        btn_sign_up.setOnClickListener{
            registerUser()
        }

    }

    /**
     * A function for actionBar Setup.
     */
    private fun setUpActionBar()
    {
        val toolbar_sign_up_activity : Toolbar
        toolbar_sign_up_activity = findViewById(R.id.toolbar_sign_up_activity)

        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.sign_up_heading)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener{
            onBackPressed()
        }

    }


    /**
     * A function to register a user to our app using the Firebase.
     * For more details visit: https://firebase.google.com/docs/auth/android/custom-auth
     */
    private fun registerUser() {
        // Here we get the text from editText and trim the space
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }
        val password2: String = et_password2.text.toString().trim { it <= ' ' }

        val phone : String = et_contact1.text.toString().trim { it <= ' ' }


        if (validateForm(name, email, password, password2, phone)) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // If the registration is successfully done
                        if (task.isSuccessful)
                        {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            // Registered Email
                            val registeredEmail = firebaseUser.email!!


                            val user = User(
                                firebaseUser.uid, name, registeredEmail, phone.toLong(), "","",ArrayList<DocumentReference>()
                            )

                            // call the registerUser function of FirestoreClass to make an entry in the database.
                            FirestoreClass().registerUser(this, user)

                        }
                        else
                        {
                            Toast.makeText(
                                this,
                                task.exception!!.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
        }
    }


    /**
     * A function to validate the entries of a new user.
     */
    private fun validateForm(name: String, email: String, password: String, password2: String, contact1 : String ): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password.")
                false
            }
            TextUtils.isEmpty(password2) -> {
                showErrorSnackBar("Please enter a password.")
                false
            }
            TextUtils.isEmpty(contact1) -> {
                showErrorSnackBar("Please enter first contact number.")
                false
            }
            !(TextUtils.equals(password,password2)) -> {
                showErrorSnackBar("Passwords do not match. Please enter the same password in both the fields")
                false
            }


            else -> {
                true
            }
        }
    }


    /**
     * A function to be called the user is registered successfully and entry is made in the firestore database.
     */
    fun userRegisteredSuccess() {

        Toast.makeText(
            this@SignUpActivity,
            "You have successfully registered.",
            Toast.LENGTH_LONG
        ).show()

        // Hide the progress dialog
        hideProgressDialog()

        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
//        FirebaseAuth.getInstance().signOut()
        // Finish the Sign-Up Screen
//        finish()
        startActivity(Intent(SignUpActivity@this, MainActivity::class.java))
    }


}