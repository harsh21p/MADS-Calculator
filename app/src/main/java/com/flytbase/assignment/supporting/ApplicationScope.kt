package com.flytbase.assignment.supporting

import android.app.Application
import android.widget.Toast
import com.flytbase.assignment.activity.MemoryRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationScope : Application() {

    lateinit var myComponent: MyComponent

    @Inject
    lateinit var memoryRef:MemoryRef

    override fun onCreate() {
        super.onCreate()

        myComponent = DaggerMyComponent.factory().create(applicationContext)
    }


}