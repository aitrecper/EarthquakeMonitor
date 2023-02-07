package recio.aitor.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import recio.aitor.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)
        val eqList = mutableListOf<Earthquake>()
        eqList.add(Earthquake("1","Buenos Aires", 4.3, 273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("2","Lima", 2.9, 273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("3","Ciudad de México", 6.0, 273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("4","Bogotá", 1.8, 273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("5","Caracas", 3.5, 273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("6","Madrid   ", 0.6, 273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("7","Acra", 5.1, 273846152L, -102.4756, 28.47365))

        val adapter = EqAdapter()

        binding.eqRecycler.adapter = adapter
        adapter.submitList(eqList)
    }

}