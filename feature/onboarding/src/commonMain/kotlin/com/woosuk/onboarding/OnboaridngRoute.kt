package com.woosuk.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.woosuk.domain.model.LoginInfo

class OnboardingRoute() : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<OnboardingScreenModel>()
        val loginInfo by screenModel.loginInfo.collectAsState()

        OnboardingScreen(
            loginInfo,
            onLogin = screenModel::login,
            onNicknameChanged = screenModel::onNicknameChanged,
            onTagChanged = screenModel::onTagChanged
        )
        LaunchedEffect(null){
            screenModel.sideEffect.collect{
                when(it){
                    is OnBoardingSideEffect.LoginFail -> println("유효하지 않은 정보에요")
                    is OnBoardingSideEffect.LoginSuccess -> println("Success")
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    loginInfo: LoginInfo,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onNicknameChanged: (String) -> Unit = {},
    onTagChanged: (String) -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = loginInfo.nickName,
            onValueChange = onNicknameChanged
        )
        TextField(
            value = loginInfo.tag,
            onValueChange = onTagChanged,
        )
        Button(onClick = {
            onLogin(loginInfo.nickName, loginInfo.tag)
        }
        ) {
            Text("완료")
        }
    }
}
