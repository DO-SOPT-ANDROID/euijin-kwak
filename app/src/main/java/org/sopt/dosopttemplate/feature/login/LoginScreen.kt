package org.sopt.dosopttemplate.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.sopt.dosopttemplate.MainActivity
import org.sopt.dosopttemplate.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    Scaffold { innerPadding ->
        MainContent(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            viewModel = viewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: MainViewModel,
) {
    val state = viewModel.state.collectAsState()
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
            fontSize = 30.sp,
            text = "Welcome to SOPT!",
        )
        Spacer(modifier = Modifier.size(64.dp))
        Text(text = "ID")
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.registerId,
            onValueChange = { textValue ->
                viewModel.updateState(
                    state.value.copy(registerId = textValue),
                )
            },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Password")
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.registerPw,
            onValueChange = { textValue ->
                viewModel.updateState(
                    state.value.copy(registerPw = textValue),
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.login()
            },
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.size(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.clearRegisterInfo()
                navController.navigate(MainActivity.Screen.SIGNUP.name)
            },
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(text = "SignUp")
        }
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = NavController(LocalContext.current),
        viewModel = MainViewModel(),
    )
}
