package com.flytbase.assignment.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.flytbase.assignment.R
import com.flytbase.assignment.supporting.ApplicationScope
import com.flytbase.assignment.supporting.CalculatorHelper
import com.flytbase.assignment.supporting.DataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.calculator.*
import javax.inject.Inject


class Calculator : AppCompatActivity() {

    @Inject
    lateinit var calculatorHelper:CalculatorHelper

    @Inject
    lateinit var memoryRef:MemoryRef

    private var myString = ""


    private var auth: FirebaseAuth?=null
    private var database= FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)
        myRef = database!!.reference

        auth = FirebaseAuth.getInstance()
        var localRef = myRef!!.child("myAuthUser").child(auth!!.uid.toString())
        try {
            database.setPersistenceEnabled(true)
            database.setPersistenceCacheSizeBytes((50 * 1000 * 1000).toLong())
        } catch (e: Exception) {
            Log.e("Calculator",e.toString())
        }

        val result = intent.getStringExtra("Result")
        if(result!=null){
            edit_text.setText(result.toString())
        }

        var myComponent = (application as ApplicationScope).myComponent
        myComponent.inject(this)

        login_logout.setOnClickListener(View.OnClickListener {
            if(auth!!.currentUser==null){
                startActivity(Intent(this,LoginSignup::class.java))
            }else{
                auth!!.signOut()
                memoryRef.stack.clear()
                login_logout.setImageDrawable(
                    ContextCompat.getDrawable(this,
                        R.drawable.ic_baseline_login_24
                    ));

                Toast.makeText(this,"SignOut",Toast.LENGTH_SHORT).show()

            }
        })
        getDataFromCloud()
        history.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,History::class.java))
        })

        one.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("1"))
        })
        two.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("2"))
        })
        three.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("3"))
        })
        four.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("4"))
        })
        five.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("5"))
        })
        six.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("6"))
        })
        seven.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("7"))
        })
        eight.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("8"))
        })
        nine.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("9"))
        })
        back_space1.setOnClickListener(View.OnClickListener {
            if(edit_text.text.toString().length >=3){
                if(edit_text.text.toString()[edit_text.text.toString().length - 1] == ' ' ){
                    edit_text.setText(getLastIndex(edit_text.text.toString(), 3))
                }else{
                    edit_text.setText(getLastIndex(edit_text.text.toString(), 1))
                }
            }else{
                edit_text.setText(getLastIndex(edit_text.text.toString(), 1))
            }
        })
        all_clear.setOnClickListener(View.OnClickListener {
            edit_text.setText("")
            pre_result.text="0"
        })
        zero.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("0"))
        })
        dot.setOnClickListener(View.OnClickListener {
            edit_text.setText(edit_text.text.append("."))
        })
        add.setOnClickListener(View.OnClickListener {
            if(edit_text.text.toString().isNotEmpty() && edit_text.text.toString()[edit_text.text.toString().length-1]!=' ') {
                edit_text.text = edit_text.text.append(" + ")
            }else{
                Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show()
            }
        })

        multi.setOnClickListener(View.OnClickListener {
            if(edit_text.text.toString().isNotEmpty() && edit_text.text.toString()[edit_text.text.toString().length-1]!=' ') {
                edit_text.text = edit_text.text.append(" * ")
            }else{
                Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show()
            }
        })

        div.setOnClickListener(View.OnClickListener {
            if(edit_text.text.toString().isNotEmpty() && edit_text.text.toString()[edit_text.text.toString().length-1]!=' ') {
                edit_text.text = edit_text.text.append(" / ")
            }else{
                Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show()
            }
        })

        sub.setOnClickListener(View.OnClickListener {
            if(edit_text.text.toString().isNotEmpty() && edit_text.text.toString()[edit_text.text.toString().length-1]!=' ') {
                edit_text.text = edit_text.text.append(" - ")
            }else{
                Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show()
            }
        })

        enter.setOnClickListener(View.OnClickListener {
            var input = edit_text.text.toString()

            if(input[input.length - 1]==' ') {
                input = getLastIndex(input, 3)
            }

            var answer = calculatorHelper!!.multiAddDivSub(input)
            pre_result.text = answer.toString()
            edit_text.setText("")
            memoryRef.stack.add(0, DataModel(input,answer))

            if(memoryRef.stack.size == 11){
                memoryRef.stack.removeAt(10)
            }
            myString=""
            for(i in memoryRef.stack){
                myString = myString+i.input+":"+i.result+","
            }
            myString = getLastIndex(myString,1)
            localRef.child("Input").setValue(myString)
        })

        edit_text.requestFocus()

        edit_text.setTextSize(45.0f)
        edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                edit_text.setSelection(s.length)
                if(s.length>=20){
                    edit_text.setTextSize(30.0f)
                }else{
                    edit_text.setTextSize(45.0f)
                }

            }
        })

        edit_text.showSoftInputOnFocus=false

    }

    private fun getLastIndex(str: String, n: Int): String {
        var r = ""
        if(str.isNotEmpty()) {
            try {
                r = str.substring(0, str.length - n)
            }catch (e:Exception){
                Log.e("String",e.toString())
            }
        }
        return r
    }



    private fun getDataFromCloud(){
        var user = auth!!.currentUser
        if(user!=null){
            login_logout.setImageDrawable(
                ContextCompat.getDrawable(this,
                    R.drawable.ic_baseline_power_settings_new_24
                ));
            memoryRef.stack.clear()
            cal_activity.visibility=View.GONE
            cal_progress.visibility=View.VISIBLE

            myRef!!.child("myAuthUser").child(auth!!.uid.toString()).get().addOnSuccessListener {
                it.child("Input").value.toString()

                var list = it.child("Input").value.toString().split(",")

                if(list[0]=="null" || list[0]=="") {


                }else{
                    for(i in list){

                        var list = i.split(":")
                        memoryRef.stack.add(0, DataModel(list[0], list[1].toFloat()))

                    }
                }
                cal_progress.visibility=View.GONE
                cal_activity.visibility=View.VISIBLE

            }.addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                cal_progress.visibility=View.GONE
                cal_activity.visibility=View.VISIBLE
            }


        }

    }

    fun hideKeyboard() {
        val imm = this
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            try {
                imm.hideSoftInputFromWindow(
                    (this as Activity).window
                        .currentFocus!!.windowToken, 0
                )
            }catch (e:Exception){
                Log.e(TAG,e.toString())
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}