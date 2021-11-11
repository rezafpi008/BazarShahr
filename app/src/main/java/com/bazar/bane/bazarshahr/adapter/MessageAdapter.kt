package com.bazar.bane.bazarshahr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bazar.bane.bazarshahr.api.model.Message
import kotlin.collections.ArrayList

class MessageAdapter constructor(
    context: Context,
    itemsList: ArrayList<Any?>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_MESSAGE_SENT = 0
        private const val TYPE_MESSAGE_RECEIVED = 1
        private const val VIEW_TYPE_LOADING = 2
    }

    var messages: ArrayList<Any?> = ArrayList()
    var context: Context? = null
    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var onClickItem: OnClickItem<Message>? = null
    private var loadMore = true
    val loadingState = 0
    val loadingSuccessState = 1
    val loadingFailState = 2
    lateinit var userId: String

    init {
        this.context = context
        this.messages = itemsList

    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setItemOnClick(onClickItem: OnClickItem<Message>) {
        this.onClickItem = onClickItem
    }

    fun setUserid(userId: String) {
        this.userId = userId
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            messages[position] is Message -> {
                if ((messages[position] as Message).send !=null)
                    TYPE_MESSAGE_SENT
                else
                    TYPE_MESSAGE_RECEIVED
            }
            else ->
                VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {
            TYPE_MESSAGE_SENT -> {
                view =
                    LayoutInflater.from(context).inflate(R.layout.item_sent_message, parent, false)
                SentMessageHolder(view)
            }
            TYPE_MESSAGE_RECEIVED -> {
                view =
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_received_message, parent, false)
                ReceiveMessageHolder(view)
            }
            else -> {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.item_loading_vertical, parent, false)
                LoadingViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SentMessageHolder -> {
                val message = messages[position] as Message
                holder.message.text = message.message
            }
            is ReceiveMessageHolder -> {
                val message = messages[position] as Message
                holder.receiveMessage.text = message.message
                holder.title.text = message.title
                holder.itemView.setOnClickListener {
                    onClickItem?.clicked(message, position)
                }
            }
            is LoadingViewHolder -> {
                holder.progressBar.isIndeterminate = true
            }
        }
    }


    override fun getItemCount(): Int {
        return messages.size
    }


    fun setLoadMore(boolean: Boolean) {
        loadMore = boolean
    }

    fun setLoading() {
        notifyDataSetChanged()
    }

    private class SentMessageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.sentText)

    }

    private class ReceiveMessageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var receiveMessage: TextView = itemView.findViewById(R.id.receivedText)
        var title: TextView = itemView.findViewById(R.id.title)
    }

}
