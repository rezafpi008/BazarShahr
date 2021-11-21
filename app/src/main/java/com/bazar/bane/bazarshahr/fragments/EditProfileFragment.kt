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
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.api.request.EditUserRequest
import com.bazar.bane.bazarshahr.api.request.UserDetailsRequest
import com.bazar.bane.bazarshahr.databinding.FragmentEditProfileBinding
import com.bazar.bane.bazarshahr.intent.UserIntent
import com.bazar.bane.bazarshahr.mainFragments.FragmentFunction
import com.bazar.bane.bazarshahr.mainFragments.ToolbarFunction
import com.bazar.bane.bazarshahr.state.UserState
import com.bazar.bane.bazarshahr.util.ToastUtil
import com.bazar.bane.bazarshahr.util.imagePicker.ImagePickerDialogFragment
import com.bazar.bane.bazarshahr.util.imagePicker.PickerBuilder
import com.bazar.bane.bazarshahr.viewModel.UserViewModel
import com.bumptech.glide.Glide

class EditProfileFragment : Fragment(), FragmentFunction, ToolbarFunction {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: UserViewModel
    private var image: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        val view = binding.root
        viewModel = UserViewModel()
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
        initialData()
        subscribeObservers()
        viewModel.setMainLoadingState(false)
        viewModel.setStateEvent(UserIntent.UserDetails(UserDetailsRequest()))
        return view
    }

    override fun initialData() {
        binding.userImg.setOnClickListener { showBottomView() }

        binding.userImgSelected.setOnClickListener { showBottomView() }

        binding.submit.setOnClickListener {
            if (checkSubmit()) {
                viewModel.setSubmitLoadingState(true)
                viewModel.setStateEvent(
                    UserIntent.EditUser(
                        EditUserRequest(
                            binding.userName.text.toString(),
                            image
                        )
                    )
                )
            }
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is UserState.GetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    viewModel.setUser(dataState.response.data?.user!!)
                    setUserImg(dataState.response.data.user.img)
                }

                is UserState.ErrorGetUserDetails -> {
                    viewModel.setMainLoadingState(false)
                    ToastUtil.showToast(dataState.error)
                }

                is UserState.EditUserDetails -> {
                    viewModel.setSubmitLoadingState(false)
                    ToastUtil.showToast(R.string.edit_profile_success)
                    findNavController().popBackStack()
                    /* viewModel.setUser(dataState.response.data?.user!!)
                     setUserImg(dataState.response.data.user.img)*/
                }
                is UserState.ErrorEditUserDetails -> {
                    viewModel.setSubmitLoadingState(false)
                    ToastUtil.showToast(R.string.try_again)
                }
            }
        })
    }

    override fun setToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.findViewById<TextView>(R.id.title_page).text = getString(R.string.edit_profile)
        toolbar.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
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
                    image = imageUri.toString()
                    setUserImg(imageUri.toString())
                }
            })
            ?.setImageName("profile")
            ?.setImageFolderName("bazarShahr")
            ?.setCustomizedUcrop(1, 1)
            ?.start()
    }

    private fun setUserImg(url: String?) {
        Glide.with(requireContext())
            .load(url)
            .placeholder(R.drawable.image_default_avatar)
            .error(R.drawable.image_default_avatar)
            .into(binding.userImgSelected)
        binding.userImgSelected.visibility = View.VISIBLE
        binding.userImg.visibility = View.GONE
    }

    private fun checkSubmit(): Boolean {
        var flag = true
        if (binding.userName.text.toString() == "") {
            binding.userName.error = getString(R.string.please_fill_this_field)
            flag = false
        }
        return flag
    }

}