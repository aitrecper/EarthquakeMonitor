package recio.aitor.earthquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.*
import recio.aitor.earthquakemonitor.Earthquake

@Dao
interface EqDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    @Query("SELECT * FROM earthquakes")
    fun getEarthquakes(): LiveData<MutableList<Earthquake>>

    @Query("SELECT * FROM earthquakes WHERE magnitude > :magnitude")
    fun getEarthquakesWithMagnitude(magnitude: Double): MutableList<Earthquake>

    @Update
    fun updateEq(vararg eq: Earthquake)

    @Delete
    fun deleteEq(vararg eq: Earthquake)
}