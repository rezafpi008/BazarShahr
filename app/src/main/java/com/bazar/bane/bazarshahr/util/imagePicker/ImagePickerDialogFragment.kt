package com.bazar.bane.bazarshahr.util.imagePicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.databinding.ImagePickerBottomSheetBinding

class ImagePickerDialogFragment(var itemClickListener: ItemClickListener) :
    BottomSheetDialogFragment() {
    lateinit var binding: ImagePickerBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_picker_bottom_sheet, container, false)
        val view: View = binding.root
        binding.cameraSelect.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClick(1)
            dismiss()
        })
        binding.gallerySelect.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClick(2)
            dismiss()
        })
        return view
    }

    interface ItemClickListener {
        fun onItemClick(item: Int)
    }
}