package com.flytbase.assignment.activity.fragments

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.login.*

class Login : Fragment() {

    private var database= FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference? = null
    lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login,container,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        try {
            database.setPersistenceEnabled(true)
            database.setPersistenceCacheSizeBytes((50 * 1000 * 1000).toLong())
        } catch (e: Exception) {
            Log.e("Login",e.toString())
        }

        auth = FirebaseAuth.getInstance()
        myRef = database!!.reference

        login_button.setOnClickListener(View.OnClickListener {

            val emailId = email.text.toString()
            val passwordIn = password.text.toString()
            if(email.text.toString().isBlank() || password.text.toString().isBlank()){
                Toast.makeText(requireActivity(),"Invalid", Toast.LENGTH_LONG).show()
            }else {
                login_button.visibility= View.GONE
                progressbar_login.visibility= View.VISIBLE

                auth.signInWithEmailAndPassword(emailId, passwordIn).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressbar_login.visibility= View.GONE
                        login_button.visibility= View.VISIBLE
                        Toast.makeText(activity, "Login Successful", Toast.LENGTH_LONG).show()
                        startActivity(Intent(requireActivity(), Calculator::class.java))
                        requireActivity().finish()

                    }
                }.addOnFailureListener { exception ->
                    login_button.visibility= View.VISIBLE
                    progressbar_login.visibility= View.GONE
                    Toast.makeText(requireActivity(), exception.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }})
    }
}