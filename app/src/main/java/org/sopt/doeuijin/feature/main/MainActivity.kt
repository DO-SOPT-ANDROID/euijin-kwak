package org.sopt.doeuijin.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.common.extension.showSnack
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.R
import org.sopt.doeuijin.databinding.ActivityMainBinding
import org.sopt.doeuijin.feature.DoAndroidFragment
import org.sopt.doeuijin.feature.HomeFragment
import org.sopt.doeuijin.feature.login.LoginActivity
import org.sopt.doeuijin.feature.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private var doubleBackToExitPressedOnce = false

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (doubleBackToExitPressedOnce) {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            } else {
                doubleBackToExitPressedOnce = true
                showSnack(binding.root) {
                    getString(org.sopt.doeuijin.R.string.press_back_again_to_exit)
                }
                lifecycleScope.launch {
                    delay(2000)
                    doubleBackToExitPressedOnce = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initExtraAndFetchView()
        initFragment()
        initBottomNavigation()
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initExtraAndFetchView() {
        intent?.run {
            viewModel.updateState(
                viewModel.state.value.copy(
                    id = getStringExtra(LoginActivity.EXTRA_ID).orEmpty(),
                    pw = getStringExtra(LoginActivity.EXTRA_PW).orEmpty(),
                    nickName = getStringExtra(LoginActivity.EXTRA_NICK_NAME).orEmpty(),
                ),
            )
        }
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fcvMain.id, MyPageFragment.newInstance())
            .commit()
    }

    private fun initBottomNavigation() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_do_android -> {
                    replaceFragment(DoAndroidFragment())
                    true
                }

                R.id.menu_mypage -> {
                    replaceFragment(MyPageFragment.newInstance())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fcvMain.id, fragment)
            .commit()
    }

    companion object {
        fun getMainIntent(context: Context, id: String, pw: String, nickName: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(LoginActivity.EXTRA_ID, id)
                putExtra(LoginActivity.EXTRA_PW, pw)
                putExtra(LoginActivity.EXTRA_NICK_NAME, nickName)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }
}