package org.sopt.doeuijin.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
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

    private var registeredId: String? = null
    private var registeredPw: String? = null
    private var registeredName: String? = null

    private val registerSignUpLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.let(::handleSuccessSignUp)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupClickListeners()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun setupClickListeners() {
        binding.btLogin.setOnClickListener { handleLogin() }
        binding.btSignUp.setOnClickListener { startSignUpActivity() }
    }

    private fun handleLogin() {
        when {
            binding.etId.text.isNotValidWith(registeredId) -> showError(R.string.login_id_error)
            binding.etPassward.text.isNotValidWith(registeredPw) -> showError(R.string.login_pw_error)
            else -> attemptLogin()
        }
    }

    private fun startSignUpActivity() {
        SignUpActivity.getSighUpIntent(this).also(registerSignUpLauncher::launch)
    }

    private fun attemptLogin() {
        runCatching {
            toast(getString(R.string.login_success))
            navigateToMainActivity()
        }.onFailure {
            Log.e("LoginActivity", "Login Error: $it")
            showError(R.string.login_error)
        }
    }

    private fun navigateToMainActivity() {
        MainActivity.getMainIntent(
            context = this,
            id = registeredId ?: throw IllegalStateException("id is null"),
            pw = registeredPw ?: throw IllegalStateException("pw is null"),
            nickName = registeredName ?: "사용자",
        ).also(::startActivity)
    }

    private fun handleSuccessSignUp(intent: Intent) {
        registeredId = intent.getStringExtra(EXTRA_ID)
        registeredPw = intent.getStringExtra(EXTRA_PW)
        registeredName = intent.getStringExtra(EXTRA_NICK_NAME)
        binding.etId.setText(registeredId)
        binding.etPassward.setText(registeredPw)
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