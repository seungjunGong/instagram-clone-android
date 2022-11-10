package com.softsquared.instagramlagame.src.main.home.post.posting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.softsquared.instagramlagame.src.main.home.post.PostRetrofitInterface
import com.softsquared.instagramlagame.src.main.home.post.posting.models.RequestUploadPostingData
import com.softsquared.instagramlagame.src.main.home.post.posting.models.UploadPostingResponse
import java.text.SimpleDateFormat
import java.util.*

class UploadPostingFragment : BaseFragment<FragmentUploadPostingBinding>(
    FragmentUploadPostingBinding::bind,
    R.layout.fragment_upload_posting) , UploadPostingFragmentInterface{

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
            uploadImage()
        }
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
                val downloadUri = task.result.toString()
                // url 값 받으면 api 호출
                val mContent = binding.uploadPostingContentEt.text.toString()
                val requestData = RequestUploadPostingData(content = mContent, hashTagList = listOf(), imgUrlList = listOf(downloadUri), reels = "N", reelsMusic = null, userTagList = listOf())
                UploadPostingService(this).tryPostUploadPosting(requestData)
            } else {
                // Handle failures
                // ...
                showCustomToast("등록에 실패 하였습니다.")
            }


        }
    }

    override fun onPostUploadPostingSuccess(response: UploadPostingResponse) {
        showCustomToast("등록 되었습니다.")
        val action = UploadPostingFragmentDirections.actionUploadPostingFragmentToHomeFragment()
        Navigation.findNavController(requireView()).navigate(action)
        showBttnav()
    }

    override fun onPostUploadPostingFailure(message: String) {
        TODO("Not yet implemented")
    }
}