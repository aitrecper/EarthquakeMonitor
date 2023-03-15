package recio.aitor.earthquakemonitor.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.api.EqJsonResponse
import recio.aitor.earthquakemonitor.api.service
import recio.aitor.earthquakemonitor.database.EqDatabase

class MainRepository(private val database: EqDatabase) {


    suspend fun fetchEarthquakes(sortByMagnitude: Boolean) : MutableList<Earthquake> {

        return withContext(Dispatchers.IO){
            val eqJsonResponse = service.getLastHourEarthquakes()
            val eqList = parseEqResult(eqJsonResponse)

            fetchEarthquakesFromDb(sortByMagnitude)
        }
    }

    suspend fun fetchEarthquakesFromDb(sortByMagnitude: Boolean) : MutableList<Earthquake> {

        return withContext(Dispatchers.IO){
            if(sortByMagnitude)
                database.eqDAO.getEarthquakesByMagnitude()
            else
                database.eqDAO.getEarthquakes()
        }
    }

    private fun parseEqResult(eqJsonResponse: EqJsonResponse) : MutableList<Earthquake>{

        val eqList = mutableListOf<Earthquake>()
        val features = eqJsonResponse.features

        for(feature in features){
            val properties = feature.properties

            val id = feature.id
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry

            val longitude = geometry.longitude
            val latitude = geometry.latitude

            eqList.add(Earthquake(id, place, magnitude, time, longitude, latitude))
        }

        return eqList
    }
}