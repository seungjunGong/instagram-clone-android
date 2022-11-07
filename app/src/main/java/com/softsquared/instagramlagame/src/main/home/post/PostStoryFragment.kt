package com.softsquared.instagramlagame.src.main.home.post

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentPostStoryBinding
import com.softsquared.instagramlagame.src.main.MainActivity
import com.softsquared.instagramlagame.src.main.home.HomeVpControllerFragment
import com.softsquared.instagramlagame.src.signup.phone_email.PhoneEmailFragmentDirections
import com.softsquared.instagramlagame.util.PermissionUtil
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PostStoryFragment : BaseFragment<FragmentPostStoryBinding>(FragmentPostStoryBinding::bind, R.layout.fragment_post_story){


    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var cameraAnimationListener: Animation.AnimationListener

    private var savedUri: Uri? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("PostStoryFragment", "onViewCreated")
        setListener()
        setCameraAnimationListener()
        permissionCheck()
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()


        // close
        binding.postStoryClose.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToHomeFragment()
            Navigation.findNavController(requireActivity(), R.id.home_vp_controller).navigate(action)
        }

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
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showCustomToast("승인 되었습니다.")
            openCamera()
        } else {
            showCustomToast("승인 거부 되었습니다.")

        }
    }

    private fun setListener() {
        binding.postStoryPhotoBt.setOnClickListener {
            savePhoto()
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
            this.binding.postCameraPreview.implementationMode = PreviewView.ImplementationMode.COMPATIBLE

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
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                    showCustomToast("오류!")
                }

            })

    }

    private fun setCameraAnimationListener() {
        cameraAnimationListener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                showCaptureImage()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        }
    }

    private fun showCaptureImage(): Boolean {
        if (binding.frameLayoutPreview.visibility == View.GONE) {
            binding.frameLayoutPreview.visibility = View.VISIBLE
            binding.imageViewPreview.setImageURI(savedUri)
            return false
        }

        return true

    }
    private fun hideCaptureImage() {
        binding.imageViewPreview.setImageURI(null)
        binding.frameLayoutPreview.visibility = View.GONE

    }


}