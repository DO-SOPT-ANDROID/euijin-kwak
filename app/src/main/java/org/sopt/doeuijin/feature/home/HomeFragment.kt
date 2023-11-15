package org.sopt.doeuijin.feature.home

import android.app.AlertDialog
import android.content.res.Configuration
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
import org.sopt.common.random.generatePaperUserName
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.R
import org.sopt.doeuijin.databinding.FragmentHomeBinding
import org.sopt.doeuijin.feature.home.profile.Profile
import org.sopt.doeuijin.feature.home.profile.ProfileAdapter
import org.sopt.doeuijin.feature.main.MainSideEffect
import org.sopt.doeuijin.feature.main.MainState
import org.sopt.doeuijin.feature.main.MainViewModel

class HomeFragment : Fragment() {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val activityViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRecyclerView()
        collectState()
        collectEvent()
    }

    private fun initView() {
        binding.fabAddFriend.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.paper_friend_add_dialog_title)
                .setPositiveButton(R.string.paper_friend_add_dialog_positive_button) { _, _ ->
                    activityViewModel.addFriend(Profile.FriendProfile(name = generatePaperUserName()))
                }
                .setNegativeButton(R.string.paper_friend_add_dialog_negative_button) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun initRecyclerView() {
        binding.rvHome.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ProfileAdapter(
                orientation = Configuration.ORIENTATION_PORTRAIT,
                onRemoveItem = {
                    activityViewModel.removeFriend(it)
                },
            )
        }
    }

    private fun collectState() {
        activityViewModel.state.flowWithLifecycle(viewLifeCycle).onEach {
            (binding.rvHome.adapter as? ProfileAdapter)?.submitList(getProfileList(it))
        }.launchIn(viewLifeCycleScope)
    }

    private fun collectEvent() {
        activityViewModel.event.flowWithLifecycle(viewLifeCycle).onEach {
            when (it) {
                is MainSideEffect.MoveToTopPage -> {
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

    private fun getProfileList(it: MainState): List<Profile> {
        val state = activityViewModel.state.value
        val currentList = it.userList.toMutableList()
        val isMyProfileExist = currentList.any { profile -> profile is Profile.MyProfile }
        if (!isMyProfileExist) currentList.add(0, Profile.MyProfile(name = state.nickName))
        return currentList.toList()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}