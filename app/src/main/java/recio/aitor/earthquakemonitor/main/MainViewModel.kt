package recio.aitor.earthquakemonitor.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.api.ApiResponseStatus
import recio.aitor.earthquakemonitor.database.getDatabase
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application, private var sortType : Boolean) : AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    private val _eqList  = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
        get() = _eqList

    init {
        reloadEarthquakes()
    }


    private fun reloadEarthquakes() {
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                _eqList.value = repository.fetchEarthquakes(sortType)
                _status.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                Log.d(TAG, "No Internet Connection", e)
                _status.value = ApiResponseStatus.ERROR
            }

        }
    }

    fun reloadEarthquakesFromDb(sortByMagnitude:Boolean) {
        viewModelScope.launch {
            _eqList.value = repository.fetchEarthquakesFromDb(sortByMagnitude)
        }
    }


}