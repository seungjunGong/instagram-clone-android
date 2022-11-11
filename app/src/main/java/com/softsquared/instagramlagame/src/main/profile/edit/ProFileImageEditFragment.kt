package com.softsquared.instagramlagame.src.main.profile.edit

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.src.main.home.HomeViewModel
import com.softsquared.instagramlagame.src.main.home.post.posting.PostPostingRVAdapter
import com.softsquared.instagramlagame.src.main.profile.edit.models.PatchProFileEditRequest
import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import com.softsquared.instagramlagame.src.main.profile.models.ResultProFileMyData
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpViewModel
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProFileImageEditFragment :
    BaseFragment<com.softsquared.instagramlagame.databinding.FragmentProfileImageEditBinding>(com.softsquared.instagramlagame.databinding.FragmentProfileImageEditBinding::bind,
        R.layout.fragment_profile_image_edit),
    ProFileEditFragmentInterface {

    lateinit var checkHomeProfile: HomeViewModel
    private var downloadUri = ""
    private val uriArr = ArrayList<Uri>()
    private var pikedImage: String = ""
    private var firebaseStorage: FirebaseStorage? = null

    val args: ProFileImageEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkHomeProfile = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        getAllPhotos()
        hideBttnav()

        firebaseStorage = Firebase.storage

        binding.editImageClose.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        binding.editImageNextBt.setOnClickListener {
            // 파이어베이스에서 받은 이미지 업로드
            binding.editImageNextBt.visibility = View.GONE
            binding.uploadImageProgressBar.visibility = View.VISIBLE
            uploadImage()
        }
    }

    private fun uploadImage() {
        val timeStamp = SimpleDateFormat("yyyMMdd_HHmmss").format(Date())
        val imageFileName = "$timeStamp.jpeg"
        val storageReference = firebaseStorage?.reference?.child(imageFileName)
        storageReference?.putFile(pikedImage.toUri())?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUri = task.result.toString()
                val data = PatchProFileEditRequest(description = args.description, link = args.link, name = args.name, nickname = args.nickname, profileUrl = downloadUri)
                // url 값 받으면 api 호출
                ProFileEditService(this).tryPatchProFileEdit(data)
            } else {
                // Handle failures
                // ...
                showCustomToast("등록에 실패 하였습니다.")
            }


        }
    }


    private fun getAllPhotos() {
        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,  //the album it in
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.MIME_TYPE
        )
        val cursor = requireContext().contentResolver
            .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(idColumn)

                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString())

                uriArr.add(uri)
            }
        }
        startRCV(uriArr)
        cursor!!.close()
    }

    private fun startRCV(data: ArrayList<Uri>) {


        binding.editImageSelectIv.setImageURI(data[0])

        val postRVAdapter = PostPostingRVAdapter(data)

        val spanCount = 4 // 3 columns

        val spacing = 10 // 50px

        val includeEdge = false
        binding.postPostingRcv.addItemDecoration(GridSpacingItemDecoration(spanCount,
            spacing,
            includeEdge))
        val layoutManager = GridLayoutManager(requireContext(), 4)

        binding.postPostingRcv.setHasFixedSize(true)
        binding.postPostingRcv.layoutManager = layoutManager
        binding.postPostingRcv.adapter = postRVAdapter

        postRVAdapter.setGalleryItemClickListener(object :
            PostPostingRVAdapter.GalleryItemClickListener {
            override fun onItemClick(uri: Uri) {
                binding.editImageSelectIv.setImageURI(null)
                binding.editImageSelectIv.setImageURI(uri)
                pikedImage = uri.toString()
            }
        })
    }

    override fun onPatchProFileSuccess(response: ProFileEditResponse) {
        // 데이터 전달
        val data = ResultProFileMyData(args.description, followerCount = 0, followingCount = 0, link = args.link, name = args.name, nickname = args.nickname, postCount = 0, profileUrl = downloadUri, userId = 0)
        Navigation.findNavController(requireView()).navigateUp()
    }

    override fun onPatchProFileFailure(message: String) {
        TODO("Not yet implemented")
    }
}