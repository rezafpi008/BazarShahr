package com.bazar.bane.bazarshahr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_ITEM
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_ITEM_HORIZONTAL_FIXED
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_ITEM_SELECTED
import com.bazar.bane.bazarshahr.adapter.AdapterConstant.Companion.VIEW_TYPE_LOADING
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bumptech.glide.Glide

class JobCategorySelectedAdapter constructor(
    context: Context,
    itemsList: ArrayList<Any?>,
    recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context: Context? = null
    private var itemsList: ArrayList<Any?> = ArrayList()
    var recyclerView: RecyclerView? = null
    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var onClickItem: OnClickItem<JobCategory>? = null
    private var loadMore = true
    private val visibleThreshold = 1
    private var lastVisibleItem = 0
    private var totalItemCount: Int = 0
    private var loadingPosition: Int = 0
    private var itemToLoad: Int = 10
    private var selectedItem = -1
    val loadingState = 0
    val loadingSuccessState = 1
    val loadingFailState = 2
    var horizontalItemFixed = false

    init {
        this.context = context
        this.itemsList = itemsList
        this.recyclerView = recyclerView
        val linearLayoutManager: LinearLayoutManager? =
            recyclerView.layoutManager as LinearLayoutManager?

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager!!.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (loadMore && totalItemCount <= lastVisibleItem + visibleThreshold && totalItemCount >= itemToLoad) {
                    if (mOnLoadMoreListener != null) {
                        setLoading(loadingState)
                        setLoadMore(false)
                        mOnLoadMoreListener!!.onLoadMore()
                    }
                }
            }
        })
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setItemOnClick(onClickItem: OnClickItem<JobCategory>) {
        this.onClickItem = onClickItem
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            itemsList[position] == null -> VIEW_TYPE_LOADING
            else -> {
                when {
                    horizontalItemFixed -> VIEW_TYPE_ITEM_HORIZONTAL_FIXED
                    selectedItem == position -> VIEW_TYPE_ITEM_SELECTED
                    else -> VIEW_TYPE_ITEM
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
                LostAuctionViewHolder(view)
            }
            VIEW_TYPE_ITEM_SELECTED -> {
                val view: View =
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_category_selected, parent, false)
                LostAuctionViewHolder(view)
            }
            VIEW_TYPE_ITEM_HORIZONTAL_FIXED -> {
                val view: View =
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_category_fixed, parent, false)
                LostAuctionViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(context)
                    .inflate(R.layout.item_loading_horizental, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LostAuctionViewHolder) {
            val item: JobCategory = itemsList[position] as JobCategory
            holder.title.text = item.name
            if (item.name == null)
                holder.title.text = item.title

            if (item.img != null)
                Glide.with(context!!)
                    .load(item.img)
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .into(holder.categoryImg)

            holder.itemView.setOnClickListener {
                selectCategory(position)
                onClickItem?.clicked(item, position)
            }
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setLoadMore(boolean: Boolean) {
        loadMore = boolean
    }

    fun setLoading(state: Int) {
        when (state) {
            0 -> {
                itemsList.add(null)
                loadingPosition = itemsList.size - 1
            }
            1 -> {
                if (itemsList.size != 0 && !loadMore)
                    itemsList.removeAt(loadingPosition)
                if (itemsList.size > loadingPosition && itemsList.size % 10 == 0)
                    setLoadMore(true)
            }
            2 -> {
                if (itemsList.size != 0)
                    itemsList.removeAt(itemsList.size - 1)
            }
        }
        notifyDataSetChanged()
    }

    fun selectCategory(position: Int) {
        selectedItem = position
        notifyDataSetChanged()
    }

    class LostAuctionViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<View>(R.id.title) as TextView
        var categoryImg: AppCompatImageView =
            itemView.findViewById<View>(R.id.img) as AppCompatImageView

    }

}