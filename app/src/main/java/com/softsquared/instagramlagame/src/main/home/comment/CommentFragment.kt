package com.softsquared.instagramlagame.src.main.home.comment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentCommentBinding

class CommentFragment : BaseFragment<FragmentCommentBinding>(FragmentCommentBinding::bind, R.layout.fragment_comment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.commentBackBt.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
            showBttnav()
        }



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    Navigation.findNavController(requireView()).navigateUp()
                    showBttnav()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}