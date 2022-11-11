package com.softsquared.instagramlagame.src.main.home.post.story

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentPostStoryBinding
import com.softsquared.instagramlagame.util.PermissionUtil
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PostStoryFragment : BaseFragment<FragmentPostStoryBinding>(FragmentPostStoryBinding::bind,
    R.layout.fragment_post_story) {


    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var cameraAnimationListener: Animation.AnimationListener

    private var savedUri: Uri? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("PostStoryFragment", "onViewCreated")

        setCameraAnimationListener()
        permissionCheck()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()


        // close
        binding.postStoryClose.setOnClickListener {
            val action = PostStoryFragmentDirections.actionPostStoryFragmentToHomeFragment()
            Navigation.findNavController(requireView()).navigate(action)
            applyWhiteColors()
        }

        binding.goPosting.setOnClickListener {
            val action = PostStoryFragmentDirections.actionPostStoryFragmentToPostPostingFragment()
            Navigation.findNavController(requireView()).navigate(action)
            applyBlackColors()
        }
        binding.postStoryPhotoBt.setOnClickListener {
            savePhoto()
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        var isNavigating = false
        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    if (showCaptureImage()) {
                        hideCaptureImage()
                    } else {
                        if (!isNavigating) {
                            isNavigating = true
                            val action =
                                PostStoryFragmentDirections.actionPostStoryFragmentToHomeFragment()
                            Navigation.findNavController(requireView()).navigate(action)
                            applyWhiteColors()
                        }
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("PostStoryFragment", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("PostStoryFragment", "onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PostStoryFragment", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PostStoryFragment", "onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("PostStoryFragment", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PostStoryFragment", "onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("PostStoryFragment", "onDetach()")
    }


    override fun onResume() {
        super.onResume()

        Log.d("PostStoryFragment", "onResume()")
    }


    // 권한 확인
    private fun permissionCheck() {

        var permissionList =
            listOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (!PermissionUtil.checkPermission(requireContext(), permissionList)) {
            PermissionUtil.requestPermission(requireActivity(), permissionList)
        } else {
            openCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showCustomToast("승인 되었습니다.")
            openCamera()
        } else {
            showCustomToast("승인 거부 되었습니다.")

        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun openCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.postCameraPreview.surfaceProvider)
                }


            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch (e: Exception) {
                Log.d("PostStoryFragment", "error : $e")
            }
        }, ContextCompat.getMainExecutor(requireContext()))

    }

    private fun savePhoto() {
        imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat("yy-mm-dd", Locale.US).format(System.currentTimeMillis()) + ".png"
        )
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture?.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    savedUri = Uri.fromFile(photoFile)
                    Log.d("PostStoryFragment", "$savedUri")
                    val animation =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.camera_shutter)
                    animation.setAnimationListener(cameraAnimationListener)
                    binding.frameLayoutShutter.animation = animation
                    binding.frameLayoutShutter.visibility = View.VISIBLE
                    binding.frameLayoutShutter.startAnimation(animation)
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                    val action = PostStoryFragmentDirections.actionPostStoryFragmentToHomeFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                    applyWhiteColors()
                    showCustomToast("오류!")
                }

            })

    }

    private fun setCameraAnimationListener() {
        cameraAnimationListener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.frameLayoutShutter.visibility = View.GONE
                showCaptureImage()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        }
    }

    private fun showCaptureImage(): Boolean {
        if (binding.frameLayoutPreview.visibility == View.GONE) {
            binding.frameLayoutPreview.visibility = View.VISIBLE
            val action =
                PostStoryFragmentDirections.actionPostStoryFragmentToUploadStoryFragment(savedUri.toString())
            Navigation.findNavController(requireView()).navigate(action)
            return false
        }
        return true
    }

    private fun hideCaptureImage() {
        binding.imageViewPreview.setImageURI(null)
        binding.frameLayoutPreview.visibility = View.GONE

    }


}