package com.flytbase.assignment.activity.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flytbase.assignment.R
import com.flytbase.assignment.activity.Calculator
import com.flytbase.assignment.activity.LoginSignup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.signup.*

class Signup : Fragment() {

    private var database= FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup,container,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        try {
            database.setPersistenceEnabled(true)
            database.setPersistenceCacheSizeBytes((50 * 1000 * 1000).toLong())
        } catch (e: Exception) {
            Log.e("Login",e.toString())
        }
        var auth= FirebaseAuth.getInstance()
        myRef = database!!.reference


        signup_button.setOnClickListener(View.OnClickListener {

            val email = signup_email.text.toString()
            val password = signup_password.text.toString()

            if (signup_email.text.toString().isBlank() || signup_password.text.toString().isBlank() || contact_no.text.toString().isBlank() || signup_confirm_password.text.toString().isBlank() || full_name.text.toString().isBlank()) {

                Toast.makeText(activity, "All fields are required", Toast.LENGTH_LONG).show()

            } else {

                if(signup_password.text.toString() == signup_confirm_password.text.toString()){

                    progress_bar_signup.visibility= View.VISIBLE
                    signup_button.visibility= View.INVISIBLE
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            var localRef = myRef!!.child("myAuthUser").child(auth!!.uid.toString())
                            try {
                                localRef!!.child("Name").setValue(full_name.text.toString()).addOnCompleteListener { task ->
                                    if(task.isSuccessful) {
                                        localRef!!.child("Email").setValue(email).addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                localRef!!.child("Contact").setValue(contact_no.text.toString()).addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        if(task.isSuccessful) {
                                                            progress_bar_signup.visibility = View.GONE
                                                            signup_button.visibility = View.VISIBLE
                                                            Toast.makeText(activity, "Signup Successful", Toast.LENGTH_LONG).show()
                                                            auth.signOut()
                                                            startActivity(Intent(activity,LoginSignup::class.java))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                Toast.makeText(activity, "Something went wrong please contact support", Toast.LENGTH_LONG).show()
                                progress_bar_signup.visibility = View.GONE
                                signup_button.visibility = View.VISIBLE
                                auth.signOut()
                            }
                        }
                    }.addOnFailureListener { exception ->
                        progress_bar_signup.visibility= View.GONE
                        signup_button.visibility= View.VISIBLE
                        Toast.makeText(activity, "Something went wrong ($exception)", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(activity, "Check password", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}