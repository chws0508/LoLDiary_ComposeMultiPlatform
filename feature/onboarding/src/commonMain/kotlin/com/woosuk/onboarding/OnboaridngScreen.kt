package com.woosuk.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.designsystem.WoosukTheme
import com.woosuk.domain.model.LoginInfo
import com.woosuk.domain.model.UserAccount

class OnboardingScreen(
    private val onShowSnackBar: (String) -> Unit,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<OnboardingScreenModel>()
        val loginInfo by screenModel.loginInfo.collectAsState()
        val recentLoginUsers by screenModel.recentLoginUsers.collectAsState()

        OnboardingScreenContent(
            loginInfo,
            onLogin = screenModel::login,
            onNicknameChanged = screenModel::onNicknameChanged,
            onTagChanged = screenModel::onTagChanged,
            recentLoginUsers = recentLoginUsers
        )

        LaunchedEffect(null) {
            screenModel.sideEffect.collect {
                when (it) {
                    is OnBoardingSideEffect.LoginFail -> {
                        onShowSnackBar(it.message)
                    }

                    is OnBoardingSideEffect.LoginSuccess -> {
                        onShowSnackBar("로그인 성공!")
                    }
                }
            }
        }
    }
}

@Composable
internal fun OnboardingScreenContent(
    loginInfo: LoginInfo,
    recentLoginUsers: List<UserAccount>,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onNicknameChanged: (String) -> Unit = {},
    onTagChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize().background(WoosukTheme.colors.Black0)
            .padding(WoosukTheme.padding.BasicHorizontalPadding)
    ) {
        Text(
            "나의 계정을 등록하세요",
            style = WoosukTheme.typography.heading5,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.BasicContentPadding)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "닉네임",
            style = WoosukTheme.typography.bodyLargeMedium,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.BasicContentPadding)
        )

        OutlinedTextField(
            value = loginInfo.nickName,
            placeholder = {
                Text(
                    "닉네임을 입력해주세요",
                    style = WoosukTheme.typography.bodyLargeRegular,
                    color = WoosukTheme.colors.Black40
                )
            },
            onValueChange = onNicknameChanged,
            textStyle = WoosukTheme.typography.bodyLargeRegular,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WoosukTheme.colors.Primary100,
                unfocusedBorderColor = WoosukTheme.colors.Black40
            ),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "태그",
            style = WoosukTheme.typography.bodyLargeMedium,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.BasicContentPadding)
        )

        OutlinedTextField(
            value = loginInfo.tag,
            placeholder = {
                Text(
                    "KR1",
                    style = WoosukTheme.typography.bodyLargeRegular,
                    color = WoosukTheme.colors.Black40
                )
            },
            onValueChange = onTagChanged,
            modifier = Modifier.fillMaxWidth(),
            textStyle = WoosukTheme.typography.bodyLargeRegular,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WoosukTheme.colors.Primary100,
                unfocusedBorderColor = WoosukTheme.colors.Black40
            ),
            leadingIcon = { Icon(imageVector = Icons.Filled.Tag, contentDescription = null) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onLogin(loginInfo.nickName, loginInfo.tag)
            },
            enabled = loginInfo.canLogin,
            colors = ButtonDefaults.buttonColors(
                containerColor = WoosukTheme.colors.Primary100,
                disabledContainerColor = WoosukTheme.colors.Black20
            ),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            Text(
                "로그인",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = WoosukTheme.typography.bodyLargeMedium
            )
        }

        Text(
            "로그인 기록",
            style = WoosukTheme.typography.heading5,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.LargeVerticalPadding)
        )

        LazyColumn {
            items(
                items = recentLoginUsers
            ) { user ->
                Text(
                    text = user.nickName + "#${user.tag}",
                    style = WoosukTheme.typography.bodyLargeRegular,
                    modifier = Modifier.clickable {
                        onLogin(user.nickName, user.tag)
                    }.padding(vertical = WoosukTheme.padding.BasicContentPadding)
                )
            }
        }
    }
}
