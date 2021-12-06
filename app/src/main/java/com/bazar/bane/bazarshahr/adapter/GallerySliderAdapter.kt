package com.texonapp.oneringgit.adapter


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.ablanco.zoomy.Zoomy
import com.bazar.bane.bazarshahr.R
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlin.collections.ArrayList


class GallerySliderAdapter(
    private val context: Context,
    bannerList: ArrayList<String>
) :
    SliderViewAdapter<GallerySliderAdapter.GalleryViewHolder>() {
    private var galleryItems: List<String> = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup): GalleryViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider_gallery, null)
        return GalleryViewHolder(inflate)
    }

    override fun onBindViewHolder(
        holder: GalleryViewHolder,
        position: Int
    ) {
        val sliderItem: String = galleryItems[position]

        Glide.with(context)
            .load(sliderItem)
            .error(R.drawable.image_default)
            .placeholder(R.drawable.image_default)
            .into(holder.imageGallery)

        val builder: Zoomy.Builder = Zoomy.Builder(context as Activity)
            .target(holder.itemView)
            .enableImmersiveMode(false)
            .animateZooming(true)
        builder.register()
    }

    override fun getCount(): Int {
        return galleryItems.size
    }

     class GalleryViewHolder(itemView: View) :
        ViewHolder(itemView) {
        var imageGallery: AppCompatImageView = itemView.findViewById(R.id.image)

    }

    init {
        galleryItems = bannerList
    }
}

