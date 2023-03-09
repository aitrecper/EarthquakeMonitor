package recio.aitor.earthquakemonitor.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.databinding.ActivityDetailsBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EARTHQUAKE_KEY = "earthquake"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras!!
        val earthquake = bundle.getParcelable<Earthquake>(EARTHQUAKE_KEY)!!

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(earthquake.time)
        val formattedString = simpleDateFormat.format(date)

        binding.magnitudeText.text = earthquake.magnitude.toString()
        binding.longitudeText.text = earthquake.longitude.toString()
        binding.latitudeText.text = earthquake.latitude.toString()
        binding.placeText.text = earthquake.place
        binding.timeText.text = formattedString

    }
}