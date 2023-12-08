package org.sopt.doeuijin.feature.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.common.extension.hideKeyboard
import org.sopt.common.extension.stringOf
import org.sopt.common.extension.toast
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.R
import org.sopt.doeuijin.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivitySignUpBinding::inflate)
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSetOnClickListener()
        initTextWatcher()
        collectEvent()
        collectState()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun initTextWatcher() {
        binding.tilId.editText?.addTextChangedListener {
            viewModel.updateId(it)
        }
        binding.tilPw.editText?.addTextChangedListener {
            viewModel.updatePw(it)
        }
        binding.tilNickname.editText?.addTextChangedListener {
            viewModel.updateNickName(it)
        }
    }

    private fun collectState() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach {
                binding.btSignUp.isEnabled = (it.isIdValid && it.isPwValid && it.isNickNameValid)
                if (it.isIdValid) handleEditTextError(SignUpError.ID)
                if (it.isPwValid) handleEditTextError(SignUpError.PW)
                if (it.isNickNameValid) handleEditTextError(SignUpError.NICK_NAME)
            }.launchIn(lifecycleScope)
    }

    private fun collectEvent() {
        viewModel.event
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is Effect.Login -> {
                        toast(stringOf(R.string.signup_success))
                        finish()
                    }

                    is Effect.Error -> {
                        handleEditTextError(it.errorType, it.messageRes)
                    }

                    is Effect.ShowToast -> {
                        toast(stringOf(it.messageRes))
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun initSetOnClickListener() {
        binding.btSignUp.setOnClickListener {
            viewModel.signUp()
        }
    }

    private fun handleEditTextError(errorType: SignUpError, errorMessageRes: Int? = null) {
        val view = when (errorType) {
            SignUpError.ID -> binding.tilId
            SignUpError.PW -> binding.tilPw
            SignUpError.NICK_NAME -> binding.tilNickname
        }
        errorMessageRes ?: let {
            view.error = null
            return
        }
        val errorMessage = stringOf(errorMessageRes)
        view.error = errorMessage
    }

    companion object {
        fun getSighUpIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}