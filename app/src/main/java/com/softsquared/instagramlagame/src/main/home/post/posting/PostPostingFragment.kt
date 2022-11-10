package com.softsquared.instagramlagame.src.main.home.post.posting

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentPostPostingBinding
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration


class PostPostingFragment : BaseFragment<com.softsquared.instagramlagame.databinding.FragmentPostPostingBinding>(FragmentPostPostingBinding::bind, R.layout.fragment_post_posting) {

    private val uriArr = ArrayList<Uri>()
    private var pikedImage : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllPhotos()
        applyBlackColors()

        binding.goStory.setOnClickListener {
            val action = PostPostingFragmentDirections.actionPostPostingFragmentToPostStoryFragment()
            Navigation.findNavController(requireView()).navigate(action)
            applyBlackColors()
        }

        binding.postPostingClose.setOnClickListener{
            val action = PostPostingFragmentDirections.actionPostPostingFragmentToHomeFragment()
            Navigation.findNavController(requireView()).navigate(action)
            applyWhiteColors()
        }

        // 다음 버튼
        binding.postPostingNextBt.setOnClickListener{
            val action = PostPostingFragmentDirections.actionPostPostingFragmentToUploadPostingFragment(pikedImage)
            Navigation.findNavController(requireView()).navigate(action)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    val action = PostPostingFragmentDirections.actionPostPostingFragmentToHomeFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                    applyWhiteColors()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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


    private fun startRCV(data: ArrayList<Uri>){


        binding.postPostingSelectIv.setImageURI(data[0])

        val postRVAdapter = PostPostingRVAdapter(data)

        val spanCount = 3 // 3 columns

        val spacing = 10 // 50px

        val includeEdge = false
        binding.postPostingRcv.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        val layoutManager = GridLayoutManager(requireContext(), 3)

        binding.postPostingRcv.setHasFixedSize(true)
        binding.postPostingRcv.layoutManager = layoutManager
        binding.postPostingRcv.adapter = postRVAdapter

        postRVAdapter.setGalleryItemClickListener(object:
            PostPostingRVAdapter.GalleryItemClickListener {
            override fun onItemClick(uri: Uri) {
                Log.d("postRV","$uri")
                binding.postPostingSelectIv.setImageURI(null)
                binding.postPostingSelectIv.setImageURI(uri)
                pikedImage = uri.toString()
            }
        })





    }


}