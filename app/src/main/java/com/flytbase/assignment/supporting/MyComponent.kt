package com.flytbase.assignment.supporting

import android.content.Context
import com.flytbase.assignment.activity.Calculator
import com.flytbase.assignment.activity.History
import com.flytbase.assignment.activity.SplashScreen
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface MyComponent {

    fun inject(calculator: Calculator)
    fun inject(splashScreen: SplashScreen)
    fun inject(history: History)


    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): MyComponent
    }

}