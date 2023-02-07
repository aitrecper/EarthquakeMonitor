package recio.aitor.earthquakemonitor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import recio.aitor.earthquakemonitor.databinding.EqListItemBinding

class EqAdapter : ListAdapter<Earthquake, EqAdapter.EqViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<Earthquake>() {

        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake) =
            oldItem == newItem
    }

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

            binding.eqMagnitudeText.text = earthquake.magnitude.toString()
            binding.eqPlaceText.text = earthquake.place
        }
    }
}