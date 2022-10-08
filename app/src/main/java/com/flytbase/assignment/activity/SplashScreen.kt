package com.flytbase.assignment.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flytbase.assignment.R
import com.flytbase.assignment.supporting.ApplicationScope
import com.flytbase.assignment.supporting.CalculatorHelper
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val splashScreenTimeout = 2000

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen, Calculator::class.java))
            finish()
        },splashScreenTimeout.toLong() )

    }

    override fun onStart() {
        super.onStart()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}