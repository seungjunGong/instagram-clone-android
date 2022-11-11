package com.softsquared.instagramlagame.src.main.home.post.story

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentUploadStoryBinding
import com.softsquared.instagramlagame.src.main.home.post.posting.UploadPostingFragmentInterface
import com.softsquared.instagramlagame.src.main.home.post.posting.models.UploadPostingResponse
import com.softsquared.instagramlagame.src.main.home.post.story.models.RequestUploadStory
import com.softsquared.instagramlagame.src.main.home.post.story.models.UploadStoryResponse
import java.text.SimpleDateFormat
import java.util.*

class UploadStoryFragment :
    BaseFragment<FragmentUploadStoryBinding>(FragmentUploadStoryBinding::bind,
        R.layout.fragment_upload_story), UploadStoryFragmentInterface{

    val args: UploadStoryFragmentArgs by navArgs()
    private var firebaseStorage: FirebaseStorage? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("upload","${args.imgUri}")
        Glide.with(requireContext()).load(args.imgUri).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/iv_profile_sample_image.PNG?alt=media&token=0953dbf7-526f-47c4-a026-1d8a00a825e0").into(binding.imageViewPreview)

        firebaseStorage = Firebase.storage

        binding.uploadStoryBt.setOnClickListener {
            // 파이어베이스에서 받은 이미지 업로드
            binding.uploadStoryBt.text = ""
            binding.uploadStoryBt.background =
                resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
            binding.uploadStoryProgressBar.visibility = View.VISIBLE
            uploadImage()
        }
    }

    private fun uploadImage() {
        val timeStamp = SimpleDateFormat("yyyMMdd_HHmmss").format(Date())
        val imageFileName = "$timeStamp.jpeg"
        val storageReference = firebaseStorage?.reference?.child(imageFileName)
        storageReference?.putFile(args.imgUri.toUri())?.continueWithTask { task ->
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
                val data = RequestUploadStory(downloadUri, listOf())
                UploadStoryService(this).tryPostUploadStory(data)
            } else {
                // Handle failures
                // ...
                showCustomToast("등록에 실패 하였습니다.")
            }


        }
    }

    override fun onPostUploadStorySuccess(response: UploadStoryResponse) {
        val action = UploadStoryFragmentDirections.actionUploadStoryFragmentToHomeFragment()
        Navigation.findNavController(requireView()).navigate(action)
        applyWhiteColors()
    }

    override fun onPostUploadStoryFailure(message: String) {
        TODO("Not yet implemented")
    }
}