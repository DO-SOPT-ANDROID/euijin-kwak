package org.sopt.doeuijin.feature.login

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.common.extension.hideKeyboard
import org.sopt.common.extension.isNotValidWith
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
                    is LoginContract.Effect.Home -> navigateToMainActivity(
                        it.id,
                        it.pw,
                        it.nickName,
                    )

                    is LoginContract.Effect.SignUp -> startSignUpActivity()
                    is LoginContract.Effect.SnackBar -> showSnack(binding.root, it.message)
                }
            }.launchIn(lifecycleScope)
    }

    private fun setupClickListeners() {
        binding.btLogin.setOnClickListener { handleLogin() }
        binding.btSignUp.setOnClickListener { startSignUpActivity() }
    }

    private fun handleLogin() {
        when {
            binding.etId.text.isNotValidWith(viewModel.state.value.id) -> showError(R.string.login_id_error)
            binding.etPassward.text.isNotValidWith(viewModel.state.value.pw) -> showError(R.string.login_pw_error)
            else -> attemptLogin()
        }
    }

    private fun startSignUpActivity() {
        SignUpActivity.getSighUpIntent(this).also(::startActivity)
    }

    private fun attemptLogin() {
        runCatching {
            toast(getString(R.string.login_success))
            viewModel.saveAutoLogin(
                id = binding.etId.id.toString(),
                pw = binding.etPassward.id.toString(),
            )
        }.onFailure {
            Log.e("LoginActivity", "Login Error: $it")
            showError(R.string.login_error)
        }
    }

    private fun navigateToMainActivity(id: String, pw: String, nickName: String) {
        MainActivity.getMainIntent(
            context = this,
            id = id,
            pw = pw,
            nickName = nickName,
        ).also(::startActivity)
    }

    private fun showError(@StringRes errorMessage: Int) {
        showSnack(binding.root) { getString(errorMessage) }
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_PW = "EXTRA_PW"
        const val EXTRA_NICK_NAME = "EXTRA_NAME"
    }
}