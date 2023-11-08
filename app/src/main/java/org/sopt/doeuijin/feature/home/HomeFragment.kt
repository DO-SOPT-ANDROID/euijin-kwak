package org.sopt.doeuijin.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.common.extension.viewLifeCycle
import org.sopt.common.extension.viewLifeCycleScope
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.FragmentHomeBinding
import org.sopt.doeuijin.feature.home.profile.ProfileAdapter
import org.sopt.doeuijin.feature.main.MainContract
import org.sopt.doeuijin.feature.main.MainViewModel

class HomeFragment : Fragment() {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val activityViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        collectState()
        collectEvent()
    }

    private fun initRecyclerView() {
        binding.rvHome.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ProfileAdapter()
        }
    }

    private fun collectState() {
        activityViewModel.state.flowWithLifecycle(viewLifeCycle).onEach {
            (binding.rvHome.adapter as? ProfileAdapter)?.submitList(it.profileList)
        }.launchIn(viewLifeCycleScope)
    }

    private fun collectEvent() {
        activityViewModel.event.flowWithLifecycle(viewLifeCycle).onEach {
            when (it) {
                is MainContract.MainSideEffect.MoveToTopPage -> {
                    runCatching {
                        binding.rvHome.scrollToPosition(0)
                    }.onFailure { t ->
                        Log.e("HomeFragment", "MoveToTopPage Error: $t")
                    }
                }

                else -> Unit
            }
        }.launchIn(viewLifeCycleScope)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}