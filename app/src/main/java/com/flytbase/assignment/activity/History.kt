package com.flytbase.assignment.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flytbase.assignment.R
import com.flytbase.assignment.supporting.ApplicationScope
import com.flytbase.assignment.supporting.DataModel
import com.flytbase.assignment.supporting.HistoryAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.history.*
import javax.inject.Inject

class History : AppCompatActivity(){

    private var mList = ArrayList<DataModel>()
    private var historyAdapter:HistoryAdapter? = null
    @Inject
    lateinit var memoryRef:MemoryRef


    private var auth: FirebaseAuth?=null
    private var database= FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        myRef = database!!.reference

        auth = FirebaseAuth.getInstance()
        var localRef = myRef!!.child("myAuthUser").child(auth!!.uid.toString())

        historyAdapter =  HistoryAdapter(mList,this@History)

        val mRecyclerView = findViewById<RecyclerView>(R.id.history)
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.adapter = historyAdapter!!

        var myComponent = (application as ApplicationScope).myComponent
        myComponent.inject(this)

        addList()

        calculator.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this,Calculator::class.java))
            finish()

        })

        clear.setOnClickListener(View.OnClickListener {

            if(auth!!.currentUser!=null){
                clear.visibility=View.INVISIBLE
                progress_delete.visibility=View.VISIBLE
                localRef.child("Input").setValue("").addOnSuccessListener {
                    mList.clear()
                    clear.visibility=View.VISIBLE
                    progress_delete.visibility=View.GONE
                }.addOnFailureListener{
                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                    clear.visibility=View.VISIBLE
                    progress_delete.visibility=View.GONE
                }
            }else{
                mList.clear()
            }

            historyAdapter!!.notifyDataSetChanged()
        })

    }

    fun onItemClick(position: Int) {



        val intent = Intent(this, Calculator::class.java)
        intent.putExtra("Result",   mList[position].result.toString())
        startActivity(intent)
        finish()


    }

    fun addList(){
        var i = 0
        mList.clear()
        while (i<memoryRef.stack.size) {
            mList.add(memoryRef.stack[i])
            i+=1
        }
        historyAdapter!!.notifyDataSetChanged()
    }

}