package recio.aitor.earthquakemonitor.main

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.api.EqJsonResponse
import recio.aitor.earthquakemonitor.api.service

class MainRepository {

    suspend fun fetchEarthquakes() : MutableList<Earthquake> {

        return withContext(Dispatchers.IO){
            val eqJsonResponse = service.getLastHourEarthquakes()


            val eqList = parseEqResult(eqJsonResponse)
            eqList
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