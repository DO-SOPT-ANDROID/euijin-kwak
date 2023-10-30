package org.sopt.dosopttemplate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.feature.home.HomeScreen
import org.sopt.dosopttemplate.feature.login.LoginScreen
import org.sopt.dosopttemplate.feature.signup.SignUpScreen
import org.sopt.dosopttemplate.ui.theme.DoEuijinKwakTheme

class MainActivity : ComponentActivity() {

    enum class Screen {
        LOGIN,
        SIGNUP,
        Home,
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoEuijinKwakTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.LOGIN.name,
                ) {
                    composable(Screen.LOGIN.name) {
                        LoginScreen(
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }
                    composable(Screen.SIGNUP.name) {
                        SignUpScreen(
                            navController =
                            navController,
                            viewModel = viewModel,
                        )
                    }
                    composable(Screen.Home.name) {
                        HomeScreen(
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }
                }
                collectEvent(navController)
            }
        }
    }

    private fun collectEvent(navController: NavHostController) {
        viewModel.event.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is MainContract.MainSideEffect.ShowToast -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                is MainContract.MainSideEffect.LoginSuccess -> {
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Home.name)
                }

                is MainContract.MainSideEffect.RegistrationSuccess -> {
                    Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack() // 로그인 화면으로 돌아갑니다.
                }
            }
        }.launchIn(lifecycleScope)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoEuijinKwakTheme {}
}
