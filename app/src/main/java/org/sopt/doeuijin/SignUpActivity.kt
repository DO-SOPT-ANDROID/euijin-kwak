package org.sopt.doeuijin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.common.extension.showSnack
import org.sopt.common.extension.stringOf
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivitySignUpBinding::inflate)

    private val idString get() = binding.etId.text.toString().trim()
    private val pwString get() = binding.etPassward.text.toString().trim()
    private val nickNameString get() = binding.etNickname.text.toString().trim()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.btSignUp.setOnClickListener {
            when {
                !validateId(idString) -> {
                    handleIdError(stringOf(R.string.signup_id_error))
                }

                !validatePassword(pwString) -> {
                    handlePwError(stringOf(R.string.signup_pw_error))
                }

                !validateNickname(nickNameString) -> {
                    handleNickNameError(stringOf(R.string.signup_nickname_error))
                }

                else -> {
                    Intent().apply {
                        putExtra(LoginActivity.EXTRA_ID, idString)
                        putExtra(LoginActivity.EXTRA_PW, pwString)
                        putExtra(LoginActivity.EXTRA_NICK_NAME, nickNameString)
                    }.let {
                        setResult(RESULT_OK, it)
                    }
                    finish()
                }
            }
        }
    }

    private fun handleNickNameError(errorMessage: String) {
        binding.etId.error = errorMessage
        showSnack(binding.root) {
            errorMessage
        }
    }

    private fun handlePwError(errorMessage: String) {
        binding.etPassward.error = errorMessage
        showSnack(binding.root) {
            errorMessage
        }
    }

    private fun handleIdError(errorMessage: String) {
        binding.etNickname.error = errorMessage
        showSnack(binding.root) {
            errorMessage
        }
    }

    private fun validateId(id: String): Boolean {
        return id.length in 6..10
    }

    private fun validatePassword(pw: String): Boolean {
        return pw.length in 8..12
    }

    private fun validateNickname(name: String): Boolean {
        return name.isNotBlank()
    }

    companion object {
        fun getSighUpIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}