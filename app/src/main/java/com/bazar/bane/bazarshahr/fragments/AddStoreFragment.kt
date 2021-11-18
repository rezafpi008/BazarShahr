package com.bazar.bane.bazarshahr.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.GalleryAdapter
import com.bazar.bane.bazarshahr.api.request.CreateJobRequest
import com.bazar.bane.bazarshahr.databinding.FragmentAddStoreBinding
import com.bazar.bane.bazarshahr.intent.AddIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.popUp.PopUpCallback
import com.bazar.bane.bazarshahr.popUp.SelectCategoryPopUp
import com.bazar.bane.bazarshahr.popUp.SelectCityPopUp
import com.bazar.bane.bazarshahr.popUp.SelectMallPopUp
import com.bazar.bane.bazarshahr.state.AddState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_JOB_ID
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.util.imagePicker.ImagePickerDialogFragment
import com.bazar.bane.bazarshahr.util.imagePicker.PickerBuilder
import com.bazar.bane.bazarshahr.viewModel.AddViewModel


class AddStoreFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentAddStoreBinding
    private lateinit var viewModel: AddViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryAdapter
    var items: ArrayList<Any?> = ArrayList()
    lateinit var categoryId: String
    var mallId: String? = null
    var cityId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_store, container, false)
        val view = binding.root
        viewModel = AddViewModel()
        binding.addViewModel = viewModel
        binding.lifecycleOwner = this
        setToolbar()
        initialData()
        subscribeObservers()
        initialRecyclerView()
        return view
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = getString(R.string.add_store_title)
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initialRecyclerView() {
        recyclerView = binding.galleryRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val horizontalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        recyclerView.layoutManager = horizontalLayoutManager
        adapter =
            GalleryAdapter(requireContext(), items)
        recyclerView.adapter = adapter
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
        if (binding.phoneNumber.text.length != 11) {
            ToastUtil.showToast(R.string.incorrect_phone_number)
            flag = false
        }
        if (binding.title.text.toString() == "") {
            binding.title.error = getString(R.string.please_fill_this_field)
            flag = false
        }
        if (binding.address.text.toString() == "") {
            binding.address.error = getString(R.string.please_fill_this_field)
            flag = false
        }
        if (binding.city.text.toString() == "") {
            ToastUtil.showToast(R.string.please_select_city)
            flag = false
        }
        if (binding.categoryTitle.text.toString() == "") {
            ToastUtil.showToast(R.string.please_select_category)
            flag = false
        }

        return flag
    }

    override fun initialData() {
        binding.addImage.setOnClickListener { showBottomView() }

        binding.city.setOnClickListener {
            SelectCityPopUp(
                requireContext(),
                cityPopUpCallback,
                this
            ).show()
        }

        binding.categoryTitle.setOnClickListener {
            SelectCategoryPopUp(
                requireContext(),
                categoryPopUpCallback,
                this
            ).show()
        }

        binding.mallTitle.setOnClickListener {
            SelectMallPopUp(
                requireContext(),
                mallPopUpCallback,
                this
            ).show()
        }

        binding.submit.setOnClickListener {
            if (checkSubmit()) {
                viewModel.setMainLoadingState(true)
                viewModel.setStateEvent(
                    AddIntent.AddJob(
                        CreateJobRequest(
                            binding.title.text.toString(),
                            cityId!!,
                            binding.address.text.toString(),
                            binding.phoneNumber.text.toString(),
                            binding.details.text.toString(),
                            categoryId,
                            mallId,
                            items
                        )
                    )
                )
            }
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is AddState.CreateJob -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast("فروشگاه با موفقیت ثبت شد.")
                    SharedPreferenceUtil.saveStringValue(
                        USER_JOB_ID,
                        dataState.response.data?.jobId
                    )
                    showUserJobId(dataState.response.data?.jobId!!)
                }

                is AddState.ErrorCreateJob -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(R.string.try_again)
                }

            }
        })
    }


    private val categoryPopUpCallback: PopUpCallback = object : PopUpCallback {
        override fun setId(id: String, title: String) {
            binding.categoryTitle.text = title
            categoryId = id
        }
    }

    private val mallPopUpCallback: PopUpCallback = object : PopUpCallback {
        override fun setId(id: String, title: String) {
            binding.mallTitle.text = title
            mallId = id
        }
    }

    private val cityPopUpCallback: PopUpCallback = object : PopUpCallback {
        override fun setId(id: String, title: String) {
            binding.city.text = getString(R.string.bane)
            cityId = id
        }
    }

    private fun showUserJobId(jobId: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("کدفروشگاه شما")
            .setMessage("کد فروشگاه شما$jobId")
            .setCancelable(false)
            .setPositiveButton("ذخیره") { dialogInterface, _ ->

                dialogInterface.dismiss()
            }.setNegativeButton("بستن") { dialogInterface, _ ->

                dialogInterface.dismiss()
            }.show()
    }

}