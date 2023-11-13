package org.sopt.doeuijin.feature.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.FragmentHomeLandscapeBinding
import org.sopt.doeuijin.feature.home.profile.ProfileAdapter
import org.sopt.doeuijin.feature.main.MainViewModel

class HomeLandscapeFragment : Fragment() {

    private val binding by viewBinding(FragmentHomeLandscapeBinding::bind)
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHomeLandscapeBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        setDotsIndicator()
    }

    private fun initViewPager() {
        binding.vpHome.run {
            adapter = ProfileAdapter(Configuration.ORIENTATION_LANDSCAPE).apply {
                submitList(viewModel.state.value.userList)
            }
        }
    }

    private fun setDotsIndicator() {
        binding.dotsIndicator.attachTo(binding.vpHome)
    }

    companion object {
        fun newInstance() = HomeLandscapeFragment()
    }
}