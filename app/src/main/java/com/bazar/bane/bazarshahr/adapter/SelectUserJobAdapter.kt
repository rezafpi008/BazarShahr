package com.bazar.bane.bazarshahr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.model.Job

class SelectUserJobAdapter constructor(
    context: Context,
    itemsList: ArrayList<Any?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_ITEM = 0
    }

    var context: Context? = null
    var itemsList: ArrayList<Any?> = ArrayList()
    private var onClickItem: OnClickJob? = null


    init {
        this.context = context
        this.itemsList = itemsList
    }


    fun setItemOnClick(onClickItem: OnClickJob) {
        this.onClickItem = onClickItem
    }


    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_user_job, parent, false)
        return JobViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JobViewHolder) {
            val item: Job = itemsList[position] as Job
            holder.title.text = item.name

            holder.itemView.setOnClickListener {
                onClickItem?.clickedInformation(item, position)
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

    class JobViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<View>(R.id.title) as TextView
    }

}