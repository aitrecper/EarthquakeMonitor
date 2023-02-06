package recio.aitor.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import recio.aitor.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
1
    }
}