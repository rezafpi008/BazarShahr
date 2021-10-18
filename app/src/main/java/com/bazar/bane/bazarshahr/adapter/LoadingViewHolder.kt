package com.bazar.bane.bazarshahr.adapter

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R


class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var progressBar: ProgressBar = itemView.findViewById<View>(R.id.progressBar) as ProgressBar
}