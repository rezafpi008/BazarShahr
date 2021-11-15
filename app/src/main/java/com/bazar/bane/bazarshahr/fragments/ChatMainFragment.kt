package com.bazar.bane.bazarshahr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.MessageAdapter
import com.bazar.bane.bazarshahr.adapter.OnClickItem
import com.bazar.bane.bazarshahr.api.model.Message
import com.bazar.bane.bazarshahr.api.request.SendMessageRequest
import com.bazar.bane.bazarshahr.databinding.FragmentChatMainBinding
import com.bazar.bane.bazarshahr.intent.MessageIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.state.MessageState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_ID
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.viewModel.MessageViewModel


abstract class ChatMainFragment : Fragment(), FragmentFunction {
    lateinit var binding: FragmentChatMainBinding
    lateinit var viewModel: MessageViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageAdapter
    private var items: ArrayList<Any?> = ArrayList()
    lateinit var title: String
    lateinit var jobId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_chat_main, container, false)
        val view = binding.root
        viewModel = MessageViewModel()
        binding.chatViewModel = viewModel
        binding.lifecycleOwner = this
        setView()
        initialData()
        subscribeObservers()
        getMessages()
        return view
    }

    override fun initialData() {
        binding.send.setOnClickListener {
            viewModel.setMessageVisibilityState(true)
            viewModel.setStateEvent(
                MessageIntent.Send(
                    SendMessageRequest(
                        jobId,
                        binding.message.text.toString()
                    )
                )
            )
        }

        items.clear()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = horizontalLayoutManager
        adapter =
            MessageAdapter(requireContext(), items)
        adapter.setUserid(SharedPreferenceUtil.getStringValue(USER_ID)!!)
        recyclerView.adapter = adapter

        adapter.setItemOnClick(object : OnClickItem<Message> {
            override fun clicked(item: Message, position: Int) {
                clickReceiveChat(item)
            }
        })

    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is MessageState.GetMessage -> {
                    viewModel.setMainLoadingState(false)
                    items.addAll(dataState.response.messages!!)
                    adapter.setLoading()
                }

                is MessageState.ErrorGetMessage -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }

                is MessageState.SendMessage -> {
                    viewModel.setMessageVisibilityState(false)
                    val message = Message(binding.message.text.toString())
                    items.add(message)
                    binding.message.setText("")
                    adapter.setLoading()
                }

                is MessageState.ErrorSendMessage -> {
                    viewModel.setMessageVisibilityState(false)
                    ToastUtil.showToast(R.string.message_unsend)
                }


            }
        })
    }

    abstract fun setView()

    abstract fun setData()

    abstract fun clickReceiveChat(item: Message)

    abstract fun getMessages()

}