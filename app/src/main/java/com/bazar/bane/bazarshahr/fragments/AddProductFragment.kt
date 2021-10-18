package com.bazar.bane.bazarshahr.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.adapter.GalleryAdapter
import com.bazar.bane.bazarshahr.databinding.FragmentAddProductBinding
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.util.imagePicker.ImagePickerDialogFragment
import com.bazar.bane.bazarshahr.util.imagePicker.PickerBuilder

class AddProductFragment : Fragment(), ToolbarFunction {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryAdapter
    var items: ArrayList<Any?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false)
        val view = binding.root
        /*viewModel = ExploreViewModel()
        binding.exploreViewModel = viewModel*/
        binding.lifecycleOwner = this
        setToolbar()
        initData()
        initialRecyclerView()
        return view
    }

    override fun setToolbar() {
    }

    private fun initData() {
        binding.addImage.setOnClickListener { showBottomView() }
    }

    private fun initialRecyclerView() {
        recyclerView = binding.galleryRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val horizontalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
            ?.setImageFolderName("MyAds")
            ?.setCustomizedUcrop(1, 1)
            ?.start()
    }

    private fun addToGallery(imageUri: Uri?) {
        items.add(imageUri.toString())
        adapter.notifyDataSetChanged()
        recyclerView.visibility = View.VISIBLE
    }

}