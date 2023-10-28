package org.sopt.dosopttemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.dosopttemplate.feature.login.LoginScreen
import org.sopt.dosopttemplate.ui.theme.DoEuijinKwakTheme

class MainActivity : ComponentActivity() {

    enum class Screen {
        LOGIN,
        SIGNUP,
        MAIN,
    }

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
                        LoginScreen(navController = navController)
                    }
                    composable(Screen.SIGNUP.name) {
                    }
                    composable(Screen.MAIN.name) {
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoEuijinKwakTheme {
        Greeting("Android")
    }
}
