package com.example.countryweather.listdata

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.countryweather.country.CountryProperty
import com.example.countryweather.databinding.GridViewItemBinding
import java.util.*

class CountryRvAdapter(val onClickListener: OnClickListener): ListAdapter<CountryProperty, CountryRvAdapter.CountryViewHolder>(
        DiffCallback) {
 fun updatelist(list :MutableList<CountryProperty>){
     submitList(list)
     notifyDataSetChanged()
 }

    object DiffCallback : DiffUtil.ItemCallback<CountryProperty>(){
        override fun areItemsTheSame(oldItem: CountryProperty, newItem: CountryProperty): Boolean {
            return oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: CountryProperty, newItem: CountryProperty): Boolean {
            return oldItem.name == newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val countryProperty = getItem(position)
        holder.bind(countryProperty)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(countryProperty)
        }


    }
    class CountryViewHolder(private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(countryProperty: CountryProperty) {
            binding.property = countryProperty
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (countryProperty: CountryProperty) -> Unit) {
        fun onClick(countryProperty: CountryProperty) = clickListener(countryProperty)
    }

}