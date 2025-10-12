package com.example.juicetracker

import android.app.Application
import com.example.juicetracker.data.AppContainer
import com.example.juicetracker.data.AppDataContainer

class JuiceTrackerApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
