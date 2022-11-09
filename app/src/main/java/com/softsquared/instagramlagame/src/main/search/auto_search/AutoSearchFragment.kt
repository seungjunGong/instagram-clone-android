package com.softsquared.instagramlagame.src.main.search.auto_search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentAutoSearchBinding
import com.softsquared.instagramlagame.src.main.search.auto_search.models.AutoSearchResponse
import com.softsquared.instagramlagame.src.main.search.auto_search.models.ResultAutoSearch

class AutoSearchFragment : BaseFragment<FragmentAutoSearchBinding>(FragmentAutoSearchBinding::bind,
    R.layout.fragment_auto_search), AutoSearchInterface {

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.autoSearchBackBt.setOnClickListener {
            requireActivity().onBackPressed()
        }


        // 텍스트 삭제
        binding.autoSearchCloseBt.setOnClickListener {
            with(binding.autoSearchEt) {
                setText("")
                setSelection(0)
                isCursorVisible = true
            }
        }

        binding.autoSearchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchTv = binding.autoSearchEt.text.toString()
                binding.autoSearchAllResultTv.visibility = View.VISIBLE
                binding.autoSearchLoadingLayout.visibility = View.VISIBLE
                binding.autoSearchResultText.text = "\"$searchTv\" 검색 중.."
                // 검색 시작
                if (!isLoading) {
                    AutoSearchService(this@AutoSearchFragment).tryGetAutoSearch(getName = searchTv,
                        getPage = 0)
                    isLoading = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                editTextCheck()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextCheck()
            }
        })

    }

    private fun editTextCheck() {
        if (binding.autoSearchEt.text.toString() == "") {
            binding.autoSearchAllResultTv
            binding.autoSearchRcv.visibility = View.GONE
            binding.autoSearchAllResultTv.visibility = View.GONE
            binding.autoSearchCloseBt.visibility = View.GONE
            binding.autoSearchLoadingLayout.visibility = View.GONE
        } else {
            binding.autoSearchCloseBt.visibility = View.VISIBLE
            binding.autoSearchLoadingLayout.visibility = View.VISIBLE
            binding.autoSearchLoadingLayout.visibility = View.GONE
        }
    }

    override fun onGetAutoSearchSuccess(response: AutoSearchResponse) {
        isLoading = false
        binding.autoSearchLoadingLayout.visibility = View.GONE

        binding.autoSearchRcv.visibility = View.VISIBLE
        val autoSearchRVD = AutoSearchRVAdapter(response.resultAutoSearch as ArrayList<ResultAutoSearch>)
        binding.autoSearchRcv.adapter = autoSearchRVD


        autoSearchRVD.setProfilItemClickListener(object : AutoSearchRVAdapter.ProfileItemClickListener{
            override fun onItemClick(userNick: String, userId: Int) {
                val action = AutoSearchFragmentDirections.actionAutoSearchFragmentToOthersProFileFragment(userNickName = userNick, userId= userId)
                Navigation.findNavController(requireView()).navigate(action)
            }

        })


    }

    override fun onGetAutoSearchFailure(message: String) {
        showCustomToast("검색 결과가 없습니다.")
        isLoading = false
        binding.autoSearchLoadingLayout.visibility = View.GONE
    }

}