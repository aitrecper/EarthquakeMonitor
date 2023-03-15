package recio.aitor.earthquakemonitor.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.R
import recio.aitor.earthquakemonitor.api.ApiResponseStatus
import recio.aitor.earthquakemonitor.databinding.ActivityMainBinding
import recio.aitor.earthquakemonitor.details.DetailsActivity
import recio.aitor.earthquakemonitor.details.DetailsActivity.Companion.EARTHQUAKE_KEY

private const val SORT_TYPE_KEY = "sort_true"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = EqAdapter(this)
        binding.eqRecycler.adapter = adapter

        val sortType = getSortType()

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(application, sortType)).get(MainViewModel::class.java)

        viewModel.eqList.observe(this, Observer {
            eqList ->
            adapter.submitList(eqList)
            handleEmptyView(eqList, binding)
        })

        viewModel.status.observe(this, Observer {
            apiResponseStatus ->
            if(apiResponseStatus == ApiResponseStatus.LOADING){
                binding.loadingWheel.visibility = View.VISIBLE
            }else if(apiResponseStatus == ApiResponseStatus.DONE){
                binding.loadingWheel.visibility = View.GONE
            }else if(apiResponseStatus == ApiResponseStatus.ERROR){
                binding.loadingWheel.visibility = View.GONE
            }
        })

        adapter.onItemClickListener = {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(EARTHQUAKE_KEY,it)
            startActivity(intent)
        }

    }

    private fun getSortType(): Boolean {
        val prefs = getPreferences(MODE_PRIVATE)
        return prefs.getBoolean(SORT_TYPE_KEY, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        if(itemId == R.id.main_menu_sort_magnitude){
            viewModel.reloadEarthquakesFromDb(true)
            saveSortType(true)
        }else if(itemId == R.id.main_menu_sort_time){
            viewModel.reloadEarthquakesFromDb(false)
            saveSortType(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveSortType(sortByMagnitude: Boolean){
        val prefs = getPreferences(MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(SORT_TYPE_KEY,sortByMagnitude)
        editor.apply()
    }

    private fun handleEmptyView(
        eqList: MutableList<Earthquake>,
        binding: ActivityMainBinding
    ) {
        if (eqList.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }

}