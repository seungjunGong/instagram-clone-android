package com.softsquared.instagramlagame.src.signup.sginup_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.softsquared.instagramlagame.config.ApplicationClass

class SignUpViewModel (application: Application, val savedStateHandle: SavedStateHandle) : AndroidViewModel(application){


    private val mutableCheckSignUp = MutableLiveData<PostSignUpRequest>()
    val checkSignUp: LiveData<PostSignUpRequest> get() = mutableCheckSignUp

    fun setCheckSignUp(subjectItem: PostSignUpRequest) {
        //여기서 postValue는 Asynctask로 백그라운드에서 유저가 클릭한 Request가 무엇인지
        // mutableCheckSignUp 라이브데이터에 업데이트시켜줍니다.
        //이렇게 하면 Activity에서 Observe가 가능해집니다.

        mutableCheckSignUp.postValue(subjectItem)

    }
}