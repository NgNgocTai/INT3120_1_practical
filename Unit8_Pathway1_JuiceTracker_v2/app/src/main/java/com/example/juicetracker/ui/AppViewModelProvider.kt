package com.example.juicetracker.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.juicetracker.JuiceTrackerApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            EntryViewModel(juiceTrackerApplication().container.trackerRepository)
        }
        initializer {
            TrackerViewModel(juiceTrackerApplication().container.trackerRepository)
        }
    }
}

// Hàm trả về đối tượng JuiceTrackerApplication từ CreationExtras
fun CreationExtras.juiceTrackerApplication(): JuiceTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as JuiceTrackerApplication)
