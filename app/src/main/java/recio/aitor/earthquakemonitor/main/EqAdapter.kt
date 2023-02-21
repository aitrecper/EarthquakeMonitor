package recio.aitor.earthquakemonitor.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import recio.aitor.earthquakemonitor.Earthquake
import recio.aitor.earthquakemonitor.R
import recio.aitor.earthquakemonitor.databinding.EqListItemBinding

private val TAG = EqAdapter::class.java.simpleName
class EqAdapter (private val context : Context) : ListAdapter<Earthquake, EqAdapter.EqViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<Earthquake>() {

        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake) =
            oldItem == newItem
    }

    lateinit var onItemClickListener: (Earthquake) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : EqAdapter.EqViewHolder {

        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))

        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {

        val earthquake = getItem(position)
        holder.bind(earthquake)

    }

    inner class EqViewHolder(private val binding: EqListItemBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(earthquake: Earthquake){

            binding.eqMagnitudeText.text = context.getString(R.string.magnitude_format, earthquake.magnitude)
            binding.eqPlaceText.text = earthquake.place
            binding.root.setOnClickListener(){
                if(::onItemClickListener.isInitialized)
                    onItemClickListener(earthquake)
                else
                    Log.e(TAG, "onItemClickListener not initialized")
            }
            binding.executePendingBindings()
        }
    }
}