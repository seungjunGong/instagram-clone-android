package com.softsquared.instagramlagame.src.signup.birthday

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.DatePicker.OnDateChangedListener
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentBirthdayBinding
import com.softsquared.instagramlagame.src.signup.id_password.IdPasswordFragmentDirections
import java.util.*


class BirthDayFragment : BaseFragment<FragmentBirthdayBinding>(FragmentBirthdayBinding::bind, R.layout.fragment_birthday) {

    private var age = 0
    private var mbirthday = ""
    private var day = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mYear = binding.birthdayDatePicker.year
        val mMonth = binding.birthdayDatePicker.month
        val mDay = binding.birthdayDatePicker.dayOfMonth

        binding.birthdayDatePicker.maxDate = Date().time

        binding.birthdayDatePicker.init(mYear, mMonth, mDay,
            OnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                var yy = year
                var mm = (monthOfYear + 1)
                var dd = dayOfMonth
                binding.birthdayDateTv.text = "${yy}년 ${mm}월 ${dd}일"
                age = mYear - yy
                mbirthday = "${yy}-${mm}"
                day = dd
                binding.birthdayAgeTv.text = "$age" + "세"
                if(age < 6){
                    binding.birthdayAgeTv.setTextColor(Color.parseColor("#E3242B"))
                } else if(mYear - yy == 6){
                    binding.birthdayAgeTv.setTextColor(Color.parseColor("#666666"))
                }
            }
        )

        binding.birthdayNext.setOnClickListener {
            if (age > 5){
                mbirthday += if(day < 10){
                    "-0$day"
                } else{
                    "-$day"
                }
                showCustomToast("$mbirthday")

                val action = BirthDayFragmentDirections.navToAgreementFragment()
                Navigation.findNavController(requireActivity(), R.id.signUp_container).navigate(action)
            }
        }
    }
}