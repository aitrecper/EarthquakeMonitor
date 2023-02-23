package recio.aitor.earthquakemonitor.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.api.ApiResponseStatus
import recio.aitor.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = EqAdapter(this)
        binding.eqRecycler.adapter = adapter

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProvider(this,
            MainViewModelFactory(application )).get(MainViewModel::class.java)

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
            Toast.makeText(this,it.place,Toast.LENGTH_SHORT).show()
        }

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