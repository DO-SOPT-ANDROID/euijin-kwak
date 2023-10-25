package org.sopt.doeuijin.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.common.extension.showSnack
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.ActivityMainBinding
import org.sopt.doeuijin.feature.login.LoginActivity

class MainActivity : AppCompatActivity() {

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
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initExtraAndFetchView() {
        intent?.run {
            binding.tvIdValue.text = getStringExtra(LoginActivity.EXTRA_ID)
            binding.tvPasswordValue.text = getStringExtra(LoginActivity.EXTRA_PW)
            binding.tvNickName.text = getStringExtra(LoginActivity.EXTRA_NICK_NAME)
        }
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