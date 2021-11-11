package com.bazar.bane.bazarshahr.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.model.Message
import com.bazar.bane.bazarshahr.api.request.MessagesRequest
import com.bazar.bane.bazarshahr.intent.MessageIntent
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.TITLE

class ChatHistories : ChatMainFragment() {

    override fun setView() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = getString(R.string.my_chats)
        toolbar.findViewById<AppCompatImageView>(R.id.back).visibility = View.INVISIBLE
        binding.messageLayout.visibility = View.INVISIBLE
    }

    override fun setData() {

    }

    override fun clickReceiveChat(item: Message) {
        val bundle = Bundle()
        bundle.putString(JOB_ID, item.from)
        bundle.putString(TITLE, item.title)
        findNavController().navigate(
            R.id.action_chatHistories_to_chat,
            bundle
        )
    }

    override fun getMessages() {
        viewModel.setStateEvent(MessageIntent.Get(MessagesRequest(null)))
    }
}