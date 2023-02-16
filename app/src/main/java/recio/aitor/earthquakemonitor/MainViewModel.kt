package recio.aitor.earthquakemonitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList : LiveData<MutableList<Earthquake>>
        get() = eqList

    init {
        coroutineScope.launch {
            _eqList.value = fetchEarthquakes()
        }
    }

    private suspend fun fetchEarthquakes() : MutableList<Earthquake> {

        return withContext(Dispatchers.IO){
            val eqList = mutableListOf<Earthquake>()
            eqList.add(Earthquake("1","Buenos Aires", 4.3, 273846152L, -102.4756, 28.47365))
            eqList.add(Earthquake("2","Lima", 2.9, 273846152L, -102.4756, 28.47365))
            eqList.add(Earthquake("3","Ciudad de México", 6.0, 273846152L, -102.4756, 28.47365))
            eqList.add(Earthquake("4","Bogotá", 1.8, 273846152L, -102.4756, 28.47365))
            eqList.add(Earthquake("5","Caracas", 3.5, 273846152L, -102.4756, 28.47365))
            eqList.add(Earthquake("6","Madrid   ", 0.6, 273846152L, -102.4756, 28.47365))
            eqList.add(Earthquake("7","Acra", 5.1, 273846152L, -102.4756, 28.47365))

            eqList
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}