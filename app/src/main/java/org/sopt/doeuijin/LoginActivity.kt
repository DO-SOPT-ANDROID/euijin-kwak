package org.sopt.doeuijin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.sopt.common.extension.showSnack
import org.sopt.common.view.viewBinding
import org.sopt.doeuijin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLoginBinding::inflate)

    private var registeredId: String? = null
    private var registeredPw: String? = null
    private var registeredName: String? = null

    private val registerSignUpLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val intent = it.data ?: return@registerForActivityResult
                handleSuccessSignUp(intent)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        initLoginClickListener()
        initSignUpClickListener()
    }

    private fun initLoginClickListener() {
        binding.btLogin.setOnClickListener {
            when {
                !isValidId() -> {
                    showSnack(binding.root) {
                        getString(R.string.login_id_error)
                    }
                }

                !isValidPw() -> {
                    showSnack(binding.root) {
                        getString(R.string.login_pw_error)
                    }
                }

                else -> {
                    runCatching {
                        navigateToMainActivity()
                    }.onFailure {
                        Log.e("LoginActivity", "Login Error Msg: $it")
                        showSnack(binding.root) { getString(R.string.login_error) }
                    }
                }
            }
        }
    }

    private fun initSignUpClickListener() {
        binding.btSignUp.setOnClickListener {
            SignUpActivity.getSighUpIntent(this).let(registerSignUpLauncher::launch)
        }
    }

    private fun navigateToMainActivity() {
        MainActivity.getMainIntent(
            context = this,
            id = registeredId ?: throw IllegalStateException("id is null"),
            pw = registeredPw ?: throw IllegalStateException("pw is null"),
            nickName = registeredName ?: "사용자",
        ).let(::startActivity)
    }

    private fun handleSuccessSignUp(intent: Intent) = with(intent) {
        registeredId = getStringExtra(EXTRA_ID)
        registeredPw = getStringExtra(EXTRA_PW)
        registeredName = getStringExtra(EXTRA_NICK_NAME)

        binding.etId.setText(registeredId)
        binding.etPassward.setText(registeredPw)
    }

    private fun isValidId(): Boolean {
        val inputId = binding.etId.text.toString()
        if (inputId.isBlank()) return false
        return inputId == registeredId
    }

    private fun isValidPw(): Boolean {
        val inputPw = binding.etPassward.text.toString()
        if (inputPw.isBlank()) return false
        return inputPw == registeredPw
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_PW = "EXTRA_PW"
        const val EXTRA_NICK_NAME = "EXTRA_NAME"
    }
}