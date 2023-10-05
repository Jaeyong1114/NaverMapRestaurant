package com.example.navermaprestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navermaprestaurant.databinding.ItemViewBinding
import com.naver.maps.geometry.LatLng

class RestaurantListAdpater(private val onClick:(LatLng) -> Unit) : ListAdapter<SearchItem,RestaurantListAdpater.ViewHolder>(diff){


    inner class ViewHolder(private val binding : ItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: SearchItem){
            binding.titleTextView.text = item.title
            binding.categoryTextView.text = item.category
            binding.locationTextView.text = item.address
            binding.root.setOnClickListener {
                onClick(LatLng(item.mapy.toDouble()/10000000,item.mapx.toDouble()/110000000))
            }

        }

    }

    companion object{
        val diff = object: DiffUtil.ItemCallback<SearchItem>(){
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.link == newItem.link
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
