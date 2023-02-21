package recio.aitor.earthquakemonitor.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import recio.aitor.earthquakemonitor.Earthquake

class MainViewModel : ViewModel() {

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList : LiveData<MutableList<Earthquake>>
        get() = _eqList

    private val repository = MainRepository()
    init {
        viewModelScope.launch {
            _eqList.value = repository.fetchEarthquakes()
        }
    }


}