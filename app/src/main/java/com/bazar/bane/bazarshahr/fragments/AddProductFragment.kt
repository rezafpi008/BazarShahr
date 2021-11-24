package com.bazar.bane.bazarshahr.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.GalleryAdapter
import com.bazar.bane.bazarshahr.api.request.CreateProductRequest
import com.bazar.bane.bazarshahr.databinding.FragmentAddProductBinding
import com.bazar.bane.bazarshahr.intent.AddIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.popUp.PopUpCallback
import com.bazar.bane.bazarshahr.popUp.SelectUserJobPopUp
import com.bazar.bane.bazarshahr.state.AddState
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_JOB_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_JOB_NAME
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.util.imagePicker.ImagePickerDialogFragment
import com.bazar.bane.bazarshahr.util.imagePicker.PickerBuilder
import com.bazar.bane.bazarshahr.viewModel.AddViewModel

class AddProductFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var viewModel: AddViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryAdapter
    var items: ArrayList<Any?> = ArrayList()
    lateinit var jobId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false)
        val view = binding.root
        viewModel = AddViewModel()
        binding.addViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        return view
    }

    override fun initialData() {

        binding.jobTitle.text = SharedPreferenceUtil.getStringValue(USER_JOB_NAME)
        jobId = SharedPreferenceUtil.getStringValue(USER_JOB_ID)!!
        /*binding.jobTitle.setOnClickListener {
            SelectUserJobPopUp(
                requireContext(),
                popUpCallback,
                this
            ).show()
        }*/

        binding.addImage.setOnClickListener { showBottomView() }
        binding.submit.setOnClickListener {
            if (checkSubmit()) {
                viewModel.setMainLoadingState(true)
                viewModel.setStateEvent(
                    AddIntent.AddProduct(
                        CreateProductRequest(
                            binding.title.text.toString(),
                            binding.details.text.toString(),
                            jobId,
                            items
                        )
                    )
                )
            }
        }

        recyclerView = binding.galleryRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val horizontalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        recyclerView.layoutManager = horizontalLayoutManager
        adapter =
            GalleryAdapter(requireContext(), items)
        recyclerView.adapter = adapter
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is AddState.CreateProduct -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(R.string.product_added)
                }

                is AddState.ErrorCreateProduct -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(R.string.try_again)
                }

            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = getString(R.string.add_product_title)
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val popUpCallback: PopUpCallback = object : PopUpCallback {
        override fun setId(id: String, title: String) {
            binding.jobTitle.text = title
            jobId = id
        }
    }

    private fun showBottomView() {
        val addPhotoBottomDialogFragment = ImagePickerDialogFragment(bottomSheetClick)
        addPhotoBottomDialogFragment.show(
            requireActivity().supportFragmentManager,
            "ImagePickerDialogFragment"
        )
    }

    private val bottomSheetClick: ImagePickerDialogFragment.ItemClickListener =
        object : ImagePickerDialogFragment.ItemClickListener {
            override fun onItemClick(item: Int) {
                if (item == 1) {
                    selectImage(PickerBuilder.SELECT_FROM_CAMERA)
                } else {
                    selectImage(PickerBuilder.SELECT_FROM_GALLERY)
                }
            }
        }

    private fun selectImage(selectType: Int) {
        PickerBuilder(activity, selectType)
            .setOnImageReceivedListener(object : PickerBuilder.onImageReceivedListener {
                override fun onImageReceived(imageUri: Uri?) {
                    addToGallery(imageUri)
                }
            })
            ?.setImageName("ads")
            ?.setImageFolderName("bazarShahr")
            ?.setCustomizedUcrop(4, 3)
            ?.start()
    }

    private fun addToGallery(imageUri: Uri?) {
        items.add(imageUri.toString())
        adapter.notifyDataSetChanged()
        recyclerView.visibility = View.VISIBLE
    }


    private fun checkSubmit(): Boolean {
        var flag = true
        if (binding.title.text.toString() == "") {
            binding.title.error = getString(R.string.please_fill_this_field)
            flag = false
        }
        if (binding.jobTitle.text.toString() == "") {
            binding.jobTitle.error = getString(R.string.please_fill_this_field)
            flag = false
        }
        return flag
    }
}