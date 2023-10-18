package org.sopt.doeuijin.feature.login

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
import org.sopt.common.extension.toast
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.R
import org.sopt.doeuijin.databinding.ActivityLoginBinding
import org.sopt.doeuijin.feature.main.MainActivity
import org.sopt.doeuijin.feature.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLoginBinding::inflate)
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupClickListeners()
        setTextWatcher()
        collectState()
        collectEvent()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun collectState() {
        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach {
                when {
                    it.isAutoLoginEnabled -> {
                        navigateToMainActivity(it.id, it.pw, it.nickName)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun collectEvent() {
        viewModel.event
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is LoginContract.Effect.LoginSuccess -> {
                        navigateToMainActivity(
                            id = it.id,
                            pw = it.pw,
                            nickName = it.nickName,
                        )
                        toast(getString(R.string.login_success))
                    }

                    is LoginContract.Effect.LoginFailed -> showSnack(binding.root) {
                        getString(R.string.login_failed)
                    }

                    is LoginContract.Effect.SignUp -> startSignUpActivity()
                    is LoginContract.Effect.IdIncorrect -> showSnack(binding.root) {
                        getString(R.string.login_id_error)
                    }

                    is LoginContract.Effect.PasswordIncorrect -> showSnack(binding.root) {
                        getString(R.string.login_pw_error)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun setTextWatcher() {
        binding.etId.addTextChangedListener {
            viewModel.updateId(it)
        }
        binding.etPassward.addTextChangedListener {
            viewModel.updatePw(it)
        }
    }

    private fun setupClickListeners() {
        binding.btLogin.setOnClickListener { viewModel.login(true) }
        binding.btSignUp.setOnClickListener { viewModel.moveToSignUp() }
    }

    private fun startSignUpActivity() {
        SignUpActivity.getSighUpIntent(this).also(::startActivity)
    }

    private fun navigateToMainActivity(id: String, pw: String, nickName: String) {
        MainActivity.getMainIntent(
            context = this,
            id = id,
            pw = pw,
            nickName = nickName,
        ).also(::startActivity)
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_PW = "EXTRA_PW"
        const val EXTRA_NICK_NAME = "EXTRA_NAME"
    }
}