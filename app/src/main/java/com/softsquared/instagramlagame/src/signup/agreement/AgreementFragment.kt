package com.softsquared.instagramlagame.src.signup.agreement

import android.os.Bundle

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentAgreementBinding
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest


class AgreementFragment : BaseFragment<FragmentAgreementBinding>(FragmentAgreementBinding::bind, R.layout.fragment_agreement) {

    private var allCheck = false
    private var serviceCheck = false
    private var dataCheck = false
    private var locationCheck = false

    private val args: AgreementFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signUpAgreementNext.setOnClickListener {

            val data = PostSignUpRequest(phoneEmail = args.getBirthDay!!.phoneEmail, birth = args.getBirthDay!!.birth, id = args.getBirthDay!!.id, password = args.getBirthDay!!.password)
            val action = AgreementFragmentDirections.navToUserNameFragment(data)
            Navigation.findNavController(requireActivity(), R.id.signUp_container).navigate(action)
        }

        binding.agreementAllIv.setOnClickListener {
            with(binding){
                if (!allCheck){
                    agreementAllIv.setImageResource(R.drawable.ic_agreement_checked)
                    agreementServiceIv.setImageResource(R.drawable.ic_agreement_checked)
                    agreementDataIv.setImageResource(R.drawable.ic_agreement_checked)
                    agreementLocationIv.setImageResource(R.drawable.ic_agreement_checked)
                    signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                    signUpAgreementNext.isClickable = true
                    serviceCheck = true
                    dataCheck = true
                    locationCheck = true
                    allCheck = true
                } else{
                    agreementAllIv.setImageResource(R.drawable.ic_agreement_unchecked)
                    agreementServiceIv.setImageResource(R.drawable.ic_agreement_unchecked)
                    agreementDataIv.setImageResource(R.drawable.ic_agreement_unchecked)
                    agreementLocationIv.setImageResource(R.drawable.ic_agreement_unchecked)
                    signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                    signUpAgreementNext.isClickable = false
                    serviceCheck = false
                    dataCheck = false
                    locationCheck = false
                    allCheck = false
                }
            }
        }
        binding.agreementServiceIv.setOnClickListener {
            if(serviceCheck){
                binding.agreementServiceIv.setImageResource(R.drawable.ic_agreement_unchecked)
                serviceCheck = false
                binding.signUpAgreementNext.isClickable = false
            } else{
                binding.agreementServiceIv.setImageResource(R.drawable.ic_agreement_checked)
                serviceCheck = true
            }
            if (serviceCheck && dataCheck && locationCheck){
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                binding.signUpAgreementNext.isClickable = true
                allCheck = true
            } else {
                binding.agreementAllIv.setImageResource(R.drawable.ic_agreement_unchecked)
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                binding.signUpAgreementNext.isClickable = false
            }
        }
        binding.agreementServiceIv.setOnClickListener {
            serviceCheck = if(serviceCheck){
                binding.agreementServiceIv.setImageResource(R.drawable.ic_agreement_unchecked)
                false
            } else{
                binding.agreementServiceIv.setImageResource(R.drawable.ic_agreement_checked)
                true
            }
            if (serviceCheck && dataCheck && locationCheck){
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                binding.signUpAgreementNext.isClickable = true
                binding.agreementAllIv.setImageResource(R.drawable.ic_agreement_checked)
                allCheck = true
            } else {
                binding.agreementAllIv.setImageResource(R.drawable.ic_agreement_unchecked)
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                binding.signUpAgreementNext.isClickable = false
                allCheck = false
            }
        }
        binding.agreementDataIv.setOnClickListener {
            dataCheck = if(dataCheck){
                binding.agreementDataIv.setImageResource(R.drawable.ic_agreement_unchecked)
                false
            } else{
                binding.agreementDataIv.setImageResource(R.drawable.ic_agreement_checked)
                true
            }
            if (serviceCheck && dataCheck && locationCheck){
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                binding.signUpAgreementNext.isClickable = true
                binding.agreementAllIv.setImageResource(R.drawable.ic_agreement_checked)
                allCheck = true
            } else {
                binding.agreementAllIv.setImageResource(R.drawable.ic_agreement_unchecked)
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                binding.signUpAgreementNext.isClickable = false
                allCheck = false
            }
        }
        binding.agreementLocationIv.setOnClickListener {
            locationCheck = if(locationCheck){
                binding.agreementLocationIv.setImageResource(R.drawable.ic_agreement_unchecked)
                false
            } else{
                binding.agreementLocationIv.setImageResource(R.drawable.ic_agreement_checked)
                true
            }
            if (serviceCheck && dataCheck && locationCheck){
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                binding.signUpAgreementNext.isClickable = true
                binding.agreementAllIv.setImageResource(R.drawable.ic_agreement_checked)
                allCheck = true
            } else {
                binding.agreementAllIv.setImageResource(R.drawable.ic_agreement_unchecked)
                binding.signUpAgreementNext.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                binding.signUpAgreementNext.isClickable = false
                allCheck = false
            }
        }
    }
}