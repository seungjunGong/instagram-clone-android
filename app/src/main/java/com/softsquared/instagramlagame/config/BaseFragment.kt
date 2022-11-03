package com.softsquared.instagramlagame.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.softsquared.instagramlagame.util.LoadingDialog
import com.softsquared.instagramlagame.util.LoadingMainDialog


// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    lateinit var mLoadingDialog: LoadingDialog
    lateinit var mAlertDialog: com.softsquared.instagramlagame.util.AlertDialog
    lateinit var mLoadingMainDialog: LoadingMainDialog

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    fun showAlertDialog(context: Context, title: String,content: String,  topBt: String, bottomBt: String) {
        mAlertDialog = com.softsquared.instagramlagame.util.AlertDialog(context, requireActivity(), title, content, topBt, bottomBt)
        mAlertDialog.show()
    }

    fun dismissAlertDialog() {
        if (mAlertDialog.isShowing) {
            mAlertDialog.dismiss()
        }
    }

    fun showLoadingMainDialog(context: Context) {
        mLoadingMainDialog = LoadingMainDialog(context)
        mLoadingMainDialog.show()
    }

    fun dismissLoadingMainDialog() {
        if (mLoadingMainDialog.isShowing) {
            mLoadingMainDialog.dismiss()
        }
    }


}