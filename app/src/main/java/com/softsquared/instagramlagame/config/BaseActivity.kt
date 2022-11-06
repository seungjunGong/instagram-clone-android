package com.softsquared.instagramlagame.config


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.softsquared.instagramlagame.util.LoadingDialog
import com.softsquared.instagramlagame.util.LoadingMainDialog
import com.softsquared.instagramlagame.util.LoginAlertDialog


// 액티비티의 기본을 작성, 뷰 바인딩 활용
abstract class BaseActivity<B : ViewBinding>(private val inflate: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B
        private set
    lateinit var mLoadingDialog: LoadingDialog
    lateinit var mAlertDialog: com.softsquared.instagramlagame.util.AlertDialog
    lateinit var mLoginAlertDialog: LoginAlertDialog
    lateinit var mLoadingMainDialog: LoadingMainDialog


    // 뷰 바인딩 객체를 받아서 inflate해서 화면을 만들어줌.
    // 즉 매번 onCreate에서 setContentView를 하지 않아도 됨.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        // 상태바 글씨 보이는 채로 투명
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        applyColors()

    }
    // Apply the title/navigation bar color
    private fun applyColors() {
        window.navigationBarColor = Color.parseColor("#FFFFFF")
    }


    // 로딩 다이얼로그, 즉 로딩창을 띄워줌.
    // 네트워크가 시작될 때 사용자가 무작정 기다리게 하지 않기 위해 작성.
    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }
    // 띄워 놓은 로딩 다이얼로그를 없앰.
    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    fun showAlertDialog(context: Context, title: String, content: String, topBt: String, bottomBt: String) {
        mAlertDialog = com.softsquared.instagramlagame.util.AlertDialog(context, this, title, content,topBt,bottomBt)
        mAlertDialog.show()
    }

    fun dismissAlertDialog() {
        if (mAlertDialog.isShowing) {
            mAlertDialog.dismiss()
        }
    }

    fun showLoginAlertDialog(context: Context, title: String, content: String, topBt: String, bottomBt: String) {
        mLoginAlertDialog = LoginAlertDialog(context, this, title, content,topBt,bottomBt)
        mLoginAlertDialog.show()
    }

    fun dismissLoginAlertDialog() {
        if (mLoginAlertDialog.isShowing) {
            mLoginAlertDialog.dismiss()
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

    // 토스트를 쉽게 띄울 수 있게 해줌.
    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}