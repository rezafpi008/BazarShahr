package com.bazar.bane.bazarshahr.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bazar.bane.bazarshahr.R
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlin.collections.ArrayList


class SliderAdapter(
    private val context: Context,
    bannerList: ArrayList<String>
) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    private var items: List<String> = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider, null)
        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(
        holder: SliderViewHolder,
        position: Int
    ) {
        val sliderItem: String = items[position]

        Glide.with(context)
            .load(sliderItem)
            .error(R.drawable.image_default)
            .placeholder(R.drawable.image_default)
            .into(holder.imgSlider)
    }

    override fun getCount(): Int {
        return items.size
    }

     class SliderViewHolder(itemView: View) :
        ViewHolder(itemView) {
        var imgSlider: AppCompatImageView = itemView.findViewById(R.id.img_slider)

    }

    init {
        items = bannerList
    }
}

