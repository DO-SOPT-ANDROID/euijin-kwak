package org.sopt.doeuijin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initExtra()
    }

    private fun initExtra() {
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
            }
        }
    }
}