package org.sopt.doeuijin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.sopt.common.extension.isValidLength
import org.sopt.common.extension.showSnack
import org.sopt.common.extension.stringOf
import org.sopt.common.extension.toast
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
                !idString.isValidLength(ID_MIN_LENGTH, ID_MAX_LENGTH) -> {
                    handleEditTextError(binding.etId, R.string.signup_id_error)
                }

                !pwString.isValidLength(PW_MIN_LENGTH, PW_MAX_LENGTH) -> {
                    handleEditTextError(binding.etPassward, R.string.signup_pw_error)
                }

                nickNameString.isBlank() -> {
                    handleEditTextError(binding.etNickname, R.string.signup_nickname_error)
                }

                else -> handleSignUpSuccess()
            }
        }
    }

    private fun handleSignUpSuccess() {
        Intent().apply {
            putExtra(LoginActivity.EXTRA_ID, idString)
            putExtra(LoginActivity.EXTRA_PW, pwString)
            putExtra(LoginActivity.EXTRA_NICK_NAME, nickNameString)
        }.let {
            setResult(RESULT_OK, it)
        }
        toast(stringOf(R.string.signup_success))
        finish()
    }

    private fun handleEditTextError(view: EditText, errorMessageRes: Int) {
        val errorMessage = stringOf(errorMessageRes)
        view.error = errorMessage
        showSnack(binding.root) { errorMessage }
    }

    companion object {
        const val ID_MIN_LENGTH = 6
        const val ID_MAX_LENGTH = 10
        const val PW_MIN_LENGTH = 8
        const val PW_MAX_LENGTH = 12

        fun getSighUpIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}