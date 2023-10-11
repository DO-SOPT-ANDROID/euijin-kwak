package org.sopt.doeuijin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.common.extension.stringOf
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivitySignUpBinding::inflate)

    private val idString get() = binding.etId.text.toString().trim()
    private val pwString get() = binding.etPassward.text.toString().trim()
    private val nameString get() = binding.etNickname.text.toString().trim()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.btSignUp.setOnClickListener {
            if (validateId(idString) && validatePassword(pwString) && validateNickname(nameString)) {
                Intent().apply {
                    putExtra(LoginActivity.EXTRA_ID, idString)
                    putExtra(LoginActivity.EXTRA_PW, pwString)
                    putExtra(LoginActivity.EXTRA_NICK_NAME, nameString)
                }.let {
                    setResult(RESULT_OK, it)
                }
                finish()
            }
        }
    }

    private fun validateId(id: String): Boolean {
        return if (id.length in 6..10) {
            true
        } else {
            binding.etId.error = stringOf(R.string.signup_id_error)
            false
        }
    }

    private fun validatePassword(pw: String): Boolean {
        return if (pw.length in 8..12) {
            true
        } else {
            binding.etPassward.error = stringOf(R.string.signup_pw_error)
            false
        }
    }

    private fun validateNickname(name: String): Boolean {
        return if (name.isNotBlank()) {
            true
        } else {
            binding.etNickname.error = stringOf(R.string.signup_nickname_error)
            false
        }
    }

    companion object {
        fun getSighUpIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}