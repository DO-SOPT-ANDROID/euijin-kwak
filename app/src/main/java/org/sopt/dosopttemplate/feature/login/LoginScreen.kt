package org.sopt.dosopttemplate.feature.login

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.sopt.dosopttemplate.MainActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
) {
    Scaffold { innerPadding ->
        MainContent(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavController,
) {
    val idTextState: MutableState<String> = remember { mutableStateOf("") }
    val passwordTextState: MutableState<String> = remember { mutableStateOf("") }
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
            fontSize = 30.sp,
            text = "Welcome to SOPT!",
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "ID")

        TextField(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            value = idTextState.value,
            onValueChange = { textValue ->
                idTextState.value = textValue
            },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Password")
        TextField(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            value = passwordTextState.value,
            onValueChange = { textValue ->
                passwordTextState.value = textValue
            },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = { navController.navigate(MainActivity.Screen.MAIN.name) }) {
            Text(text = "Login")
        }
    }
}
