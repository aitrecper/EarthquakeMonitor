package recio.aitor.earthquakemonitor.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val application : Application, private var sortType : Boolean) :
    ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(application, sortType) as T
        }
    }
