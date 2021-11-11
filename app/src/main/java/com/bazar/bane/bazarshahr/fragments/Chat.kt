package com.bazar.bane.bazarshahr.fragments

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.model.Message
import com.bazar.bane.bazarshahr.api.request.MessagesRequest
import com.bazar.bane.bazarshahr.intent.MessageIntent
import com.bazar.bane.bazarshahr.util.AppConstants

class Chat : ChatMainFragment() {
    override fun setView() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = title
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setData() {
        arguments?.let {
            title = it.getString(AppConstants.TITLE)!!
            jobId = it.getString(AppConstants.JOB_ID)!!
        }
    }

    override fun clickReceiveChat(item: Message) {

    }

    override fun getMessages() {
        viewModel.setStateEvent(MessageIntent.Get(MessagesRequest(jobId)))
    }
}