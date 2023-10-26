package org.sopt.doeuijin.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}