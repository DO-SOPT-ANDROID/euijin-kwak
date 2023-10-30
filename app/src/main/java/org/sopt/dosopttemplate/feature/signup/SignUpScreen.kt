package org.sopt.dosopttemplate.feature.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.sopt.dosopttemplate.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MainViewModel,
) {
    Scaffold { innerPadding ->
        SignUpContent(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            viewModel = viewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: MainViewModel,
) {
    val state = viewModel.state.collectAsState()
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
            fontSize = 30.sp,
            text = "SignUp!",
        )
        Spacer(modifier = Modifier.size(64.dp))
        Text(text = "ID")
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.id,
            onValueChange = { textValue ->
                viewModel.updateState(
                    state.value.copy(id = textValue),
                )
            },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Password")
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.pw,
            onValueChange = { textValue ->
                viewModel.updateState(
                    state.value.copy(pw = textValue),
                )
            },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "NickName")
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.nickname,
            onValueChange = { textValue ->
                viewModel.updateState(
                    state.value.copy(nickname = textValue),
                )
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.popBackStack()
            },
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        modifier = Modifier,
        navController = NavController(LocalContext.current),
        viewModel = MainViewModel(),
    )
}
