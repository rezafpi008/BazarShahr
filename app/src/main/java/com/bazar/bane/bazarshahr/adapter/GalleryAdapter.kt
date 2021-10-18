package com.bazar.bane.bazarshahr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_ITEM
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_LOADING
import com.bumptech.glide.Glide


class GalleryAdapter constructor(
    context: Context,
    itemsList: ArrayList<Any?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context: Context? = null
    var itemsList: ArrayList<Any?> = ArrayList()

    init {
        this.context = context
        this.itemsList = itemsList
    }


    override fun getItemViewType(position: Int): Int {
        return if (itemsList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false)
            return MyFavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyFavouritesViewHolder) {
            val item: String = itemsList[position] as String
            Glide.with(context!!)
                .load(item)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .into(holder.galleryImg)
            holder.removeImage.setOnClickListener {
                removeItem(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }


    private fun removeItem(position: Int) {
        itemsList.removeAt(position)
        notifyDataSetChanged()
    }

    class MyFavouritesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var galleryImg: AppCompatImageView =
            itemView.findViewById<View>(R.id.gallery_img) as AppCompatImageView
        var removeImage: AppCompatImageView =
            itemView.findViewById<View>(R.id.remove_img) as AppCompatImageView

    }

}