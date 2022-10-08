package com.flytbase.assignment.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.flytbase.assignment.R
import com.flytbase.assignment.activity.fragments.Login
import com.flytbase.assignment.activity.fragments.Signup
import kotlinx.android.synthetic.main.login_signup.*

class LoginSignup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup)


        val fragmentLogin = Login()
        val fragmentSignup = Signup()
        showFragment(fragmentLogin)

        text_change.setOnClickListener(View.OnClickListener {
            if(go_to_signup_page.text=="Login"){
                showFragment(fragmentLogin)
                third_text.text = "Don’t have an account?"
                go_to_signup_page.text = "Signup"

            }else {
                showFragment(fragmentSignup)
                third_text.text = "Already have an account?"
                go_to_signup_page.text = "Login"
            }
        })

    }

    override fun onResume() {
        super.onResume()
        third_text.text = "Don’t have an account?"
        go_to_signup_page.text = "Signup"
    }

    private fun showFragment(fragment: Fragment){
        try{
            val frame = supportFragmentManager.beginTransaction()
            frame.replace(R.id.fragment_main,fragment)
            frame.commit()
        }catch (e:Exception){
            Log.e("LoginSignup",e.toString())
        }
    }
}