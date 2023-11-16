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
import org.sopt.common.extension.showSnack
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
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun initTextWatcher() {
        binding.etId.addTextChangedListener {
            viewModel.updateId(it)
        }
        binding.etPassward.addTextChangedListener {
            viewModel.updatePw(it)
        }
        binding.etNickname.addTextChangedListener {
            viewModel.updateNickName(it)
        }
    }

    private fun collectEvent() {
        viewModel.event
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is SignUpContract.Effect.Login -> {
                        toast(stringOf(R.string.signup_success))
                        finish()
                    }

                    is SignUpContract.Effect.Error -> {
                        handleEditTextError(it.errorType, it.messageRes)
                    }

                    is SignUpContract.Effect.ShowToast -> {
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

    private fun handleEditTextError(errorType: SignUpError, errorMessageRes: Int) {
        val view = when (errorType) {
            SignUpError.ID -> binding.etId
            SignUpError.PW -> binding.etPassward
            SignUpError.NICK_NAME -> binding.etNickname
        }
        val errorMessage = stringOf(errorMessageRes)
        view.error = errorMessage
        showSnack(binding.root, errorMessage)
    }

    companion object {
        fun getSighUpIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}