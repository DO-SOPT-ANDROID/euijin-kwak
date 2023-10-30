package org.sopt.dosopttemplate.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.sopt.dosopttemplate.MainViewModel
import org.sopt.dosopttemplate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MainViewModel,
) {
    Scaffold { innerPadding ->
        HomeContent(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            viewModel = viewModel,
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: MainViewModel,
) {
    val state = viewModel.state.collectAsState()
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "profile",
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = state.value.nickname,
                style = TextStyle(
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                ),
                color = Color.Black,
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "컴포즈 반가워",
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "ID",
            style = TextStyle(
                fontWeight = FontWeight(700),
                fontSize = 30.sp,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = state.value.id)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "PW",
            style = TextStyle(
                fontWeight = FontWeight(700),
                fontSize = 30.sp,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = state.value.pw)
    }
}
