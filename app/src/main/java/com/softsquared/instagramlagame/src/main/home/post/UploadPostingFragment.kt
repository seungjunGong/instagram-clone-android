package com.softsquared.instagramlagame.src.main.home.post

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentUploadPostingBinding
import java.text.SimpleDateFormat
import java.util.*

class UploadPostingFragment : BaseFragment<FragmentUploadPostingBinding>(
    FragmentUploadPostingBinding::bind,
    R.layout.fragment_upload_posting) {

    private val args: UploadPostingFragmentArgs by navArgs()
    private var firebaseStorage: FirebaseStorage? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadPostingIv.setImageURI(args.pickedImage.toUri())

        firebaseStorage = Firebase.storage

        binding.uploadPostingBackBt.setOnClickListener {
            requireActivity().onBackPressed()
            applyBlackColors()
        }

        binding.uploadPostingCompleteBt.setOnClickListener {
            // 파이어베이스에서 받은 이미지 업로드
            binding.uploadPostingCompleteBt.visibility = View.GONE
            binding.uploadPostingProgressBar.visibility = View.VISIBLE
            val text = binding.uploadPostingContentEt.text
            uploadImage()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    requireActivity().onBackPressed()
                    applyBlackColors()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun uploadImage() {
        val timeStamp = SimpleDateFormat("yyyMMdd_HHmmss").format(Date())
        val imageFileName = "$timeStamp.jpeg"
        val storageReference = firebaseStorage?.reference?.child(imageFileName)
        storageReference?.putFile(args.pickedImage.toUri())?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                // url 값 받으면 api 호출
            } else {
                // Handle failures
                // ...
            }


        }
    }
}