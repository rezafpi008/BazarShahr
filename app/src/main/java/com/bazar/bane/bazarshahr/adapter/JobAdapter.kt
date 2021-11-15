package com.bazar.bane.bazarshahr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.model.Job
import com.bumptech.glide.Glide

class JobAdapter constructor(
    context: Context,
    itemsList: ArrayList<Any?>,
    recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_ITEM_HORIZONTAL = 2
        private const val VIEW_TYPE_ITEM_HORIZONTAL_FIXED = 3
        private const val VIEW_TYPE_LOADING_VERTICAL = 1
        private const val VIEW_TYPE_LOADING_HORIZONTAL = 4
    }

    var context: Context? = null
    var itemsList: ArrayList<Any?> = ArrayList()
    var recyclerView: RecyclerView? = null
    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var onClickItem: OnClickJob? = null
    private var loadMore = true
    private val visibleThreshold = 1
    private var lastVisibleItem = 0
    private var totalItemCount: Int = 0
    private var loadingPosition: Int = 0
    private var itemToLoad: Int = 10
    val loadingState = 0
    val loadingSuccessState = 1
    val loadingFailState = 2
    var horizontalItem = false
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

    fun setItemOnClick(onClickItem: OnClickJob) {
        this.onClickItem = onClickItem
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            itemsList[position] == null -> {
                if (horizontalItem || horizontalItemFixed)
                    VIEW_TYPE_LOADING_HORIZONTAL
                else
                    VIEW_TYPE_LOADING_VERTICAL
            }
            horizontalItem -> {
                VIEW_TYPE_ITEM_HORIZONTAL
            }
            horizontalItemFixed -> {
                VIEW_TYPE_ITEM_HORIZONTAL_FIXED
            }
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.item_job, parent, false)
                JobViewHolder(view)
            }
            VIEW_TYPE_ITEM_HORIZONTAL -> {
                val view: View =
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_job_horizontal, parent, false)
                JobViewHolder(view)
            }
            VIEW_TYPE_ITEM_HORIZONTAL_FIXED -> {
                val view: View =
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_job_horizontal_fixed, parent, false)
                JobViewHolder(view)
            }
            VIEW_TYPE_LOADING_VERTICAL -> {
                val view: View = LayoutInflater.from(context)
                    .inflate(R.layout.item_loading_vertical, parent, false)
                LoadingViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(context)
                    .inflate(R.layout.item_loading_horizental, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JobViewHolder) {
            val item: Job = itemsList[position] as Job
            holder.title.text = item.name
            if (item.img != null)
                Glide.with(context!!)
                    .load(item.img)
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .into(holder.jobImg)

            holder.itemView.setOnClickListener {
                onClickItem?.clickedInformation(item, position)
            }

            holder.products.setOnClickListener {
                onClickItem?.clickedProducts(item, position)
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

    fun removeItem(position: Int) {
        itemsList.removeAt(position)
        notifyDataSetChanged()
    }

    class JobViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var jobImg: AppCompatImageView =
            itemView.findViewById<View>(R.id.image) as AppCompatImageView
        var title: TextView = itemView.findViewById<View>(R.id.title) as TextView
        var products: AppCompatButton =
            itemView.findViewById<View>(R.id.products_btn) as AppCompatButton

    }

}