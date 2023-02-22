package recio.aitor.earthquakemonitor.main

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.database.getDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    val eqList  = repository.eqList

    init {
        viewModelScope.launch {
            repository.fetchEarthquakes()
        }
    }


}