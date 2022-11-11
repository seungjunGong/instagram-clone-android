package com.softsquared.instagramlagame.src.main.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.softsquared.instagramlagame.src.main.profile.models.ResultProFileMyData
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest

class HomeViewModel (application: Application, val savedStateHandle: SavedStateHandle) : AndroidViewModel(application){


    private val mutableCheckProfile = MutableLiveData<ResultProFileMyData>()
    val checkProfile: LiveData<ResultProFileMyData> get() = mutableCheckProfile

    fun setCheckProfile(subjectItem:ResultProFileMyData) {
        //여기서 postValue는 Asynctask로 백그라운드에서 유저가 클릭한 Request가 무엇인지
        // mutableCheckSignUp 라이브데이터에 업데이트시켜줍니다.
        //이렇게 하면 Activity에서 Observe가 가능해집니다.

        mutableCheckProfile.postValue(subjectItem)

    }
}