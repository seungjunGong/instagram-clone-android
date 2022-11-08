package com.softsquared.instagramlagame.src.main.home.post

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentPostPostingBinding
import java.io.FileNotFoundException


class PostPostingFragment : BaseFragment<com.softsquared.instagramlagame.databinding.FragmentPostPostingBinding>(FragmentPostPostingBinding::bind, R.layout.fragment_post_posting) {

    private val uriArr = ArrayList<Uri>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllPhotos()

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

    }



}