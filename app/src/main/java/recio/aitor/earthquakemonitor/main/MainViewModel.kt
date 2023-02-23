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
class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    val eqList  = repository.eqList

    init {
        viewModelScope.launch {
            try{
                _status.value = ApiResponseStatus.LOADING
                repository.fetchEarthquakes()
                _status.value = ApiResponseStatus.DONE
            }catch (e: UnknownHostException){
                Log.d(TAG,"No Internet Connection", e)
                _status.value = ApiResponseStatus.ERROR
            }

        }
    }


}