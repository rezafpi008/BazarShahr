package com.bazar.bane.bazarshahr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_ITEM
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_LOADING
import com.bazar.bane.bazarshahr.api.model.City


class CityAdapter constructor(
    context: Context,
    itemsList: ArrayList<Any?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context: Context? = null
    private var itemsList: ArrayList<Any?> = ArrayList()
    var recyclerView: RecyclerView? = null
    private var onClickItem: OnClickItem<City>? = null
    val loadingState = 0
    val loadingSuccessState = 1
    val loadingFailState = 2
    var horizontalItemFixed = false

    init {
        this.context = context
        this.itemsList = itemsList
    }

    fun setItemOnClick(onClickItem: OnClickItem<City>) {
        this.onClickItem = onClickItem
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            itemsList[position] == null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.item_city, parent, false)
                CityViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(context)
                    .inflate(R.layout.item_loading_horizental, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CityViewHolder) {
            val item: City = itemsList[position] as City
            holder.title.text = item.name
            holder.itemView.setOnClickListener {
                onClickItem?.clicked(item, position)
            }
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setLoading() {
        notifyDataSetChanged()
    }

    class CityViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<View>(R.id.title) as TextView
    }

}