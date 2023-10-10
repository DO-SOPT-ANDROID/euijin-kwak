package org.sopt.doeuijin

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.doeuijin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    enum class LoginType {

    }

    private lateinit var binding: ActivityLoginBinding
    private var registeredId: String? = null
    private var registeredPw: String? = null
    private var registeredName: String? = null

    private val registerSignUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            it.data?.run {
                registeredId = getStringExtra(EXTRA_ID)
                registeredPw = getStringExtra(EXTRA_PW)
                registeredName = getStringExtra(EXTRA_NICK_NAME)

                binding.etId.setText(registeredId)
                binding.etPassward.setText(registeredPw)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.btLogin.setOnClickListener {
            when {
                !isValidId() -> {

                }

                !isValidPw() -> {

                }

                else -> {
                    navigateToMainActivity()
                }
            }
        }

        binding.btSignUp.setOnClickListener {
            SignUpActivity.getSighUpIntent(this).let(registerSignUpLauncher::launch)
        }
    }

    private fun navigateToMainActivity() {
        val inputId = binding.etId.text.toString().trim()
        val inputPw = binding.etPassward.text.toString().trim()
        MainActivity.getMainIntent(
                context = this,
                id = inputId,
                pw = inputPw,
                nickName = registeredName ?: "사용자"
        ).let(::startActivity)
    }

    private fun isValidId(): Boolean {
        val inputId = binding.etId.text.toString().trim()
        return inputId == registeredId
    }

    private fun isValidPw(): Boolean {
        val inputPw = binding.etPassward.text.toString().trim()
        return inputPw == registeredPw
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_PW = "EXTRA_PW"
        const val EXTRA_NICK_NAME = "EXTRA_NAME"
    }
}