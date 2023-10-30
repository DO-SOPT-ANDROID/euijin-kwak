package org.sopt.doeuijin.feature.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.common.extension.viewLifeCycle
import org.sopt.common.extension.viewLifeCycleScope
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.FragmentMyPageBinding
import org.sopt.doeuijin.feature.main.MainViewModel

class MyPageFragment : Fragment() {

    private val binding by viewBinding(FragmentMyPageBinding::bind)

    private val activityViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentMyPageBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
    }

    private fun collectState() {
        activityViewModel.state.flowWithLifecycle(viewLifeCycle).onEach {
            binding.tvIdValue.text = it.id
            binding.tvPasswordValue.text = it.pw
            binding.tvNickName.text = it.nickName
        }.launchIn(viewLifeCycleScope)
    }

    companion object {
        fun newInstance() = MyPageFragment()
    }
}