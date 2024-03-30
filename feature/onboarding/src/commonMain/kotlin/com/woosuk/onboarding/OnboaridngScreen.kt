package com.woosuk.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.model.Account
import com.woosuk.domain.model.LoginInfo
import com.woosuk.navigation.SharedScreen
import kotlinx.coroutines.flow.collectLatest

class OnboardingScreen() : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<OnboardingScreenModel>()
        val loginInfo by screenModel.loginInfo.collectAsState()
        val recentLoginUsers by screenModel.recentLoginUsers.collectAsState()
        val isLoading by screenModel.isLoading.collectAsState()
        val snackBarController = LocalSnackbarController.current
        val navigator = LocalNavigator.currentOrThrow
        val tabScreen = rememberScreen(SharedScreen.TabScreen(LocalNavigator.currentOrThrow))
        Scaffold(
            snackbarHost = { SnackbarHost(LocalSnackbarController.current.snackBarHostState) },
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(it)) {
                OnboardingScreenContent(
                    loginInfo,
                    onLogin = screenModel::login,
                    onNicknameChanged = screenModel::onNicknameChanged,
                    onTagChanged = screenModel::onTagChanged,
                    recentLoginUsers = recentLoginUsers,
                    isLoading = isLoading,
                )
            }
        }

        LaunchedEffect(null) {
            screenModel.sideEffect.collectLatest {
                when (it) {
                    is OnBoardingSideEffect.LoginFail -> {
                        when (it.errorState.errorCode) {
                            401 -> snackBarController.showMessage("잘못된 경로로 접속하고 있어요")
                            403 -> snackBarController.showMessage("로그인 할 수 없어요. 관리자에게 문의해주세요")
                            404 -> snackBarController.showMessage("존재하지 않는 닉네임 또는 태그입니다")
                            else -> snackBarController.showMessage("네트워크를 확인해주세요")
                        }
                    }

                    is OnBoardingSideEffect.LoginSuccess -> {
                        navigator.replace(tabScreen)
                    }
                }
            }
        }
    }
}

@Composable
internal fun OnboardingScreenContent(
    loginInfo: LoginInfo,
    recentLoginUsers: List<Account>,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onNicknameChanged: (String) -> Unit = {},
    onTagChanged: (String) -> Unit = {},
    isLoading: Boolean,
) {
    Column(
        modifier =
            Modifier.fillMaxSize().background(WoosukTheme.colors.Black0)
                .padding(WoosukTheme.padding.BasicHorizontalPadding),
    ) {
        Text(
            "나의 계정을 등록하세요",
            style = WoosukTheme.typography.heading5,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.BasicContentPadding),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "닉네임",
            style = WoosukTheme.typography.bodyLargeMedium,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.BasicContentPadding),
        )

        OutlinedTextField(
            value = loginInfo.nickName,
            placeholder = {
                Text(
                    "닉네임을 입력해주세요",
                    style = WoosukTheme.typography.bodyLargeRegular,
                    color = WoosukTheme.colors.Black40,
                )
            },
            onValueChange = onNicknameChanged,
            textStyle = WoosukTheme.typography.bodyLargeRegular,
            modifier = Modifier.fillMaxWidth(),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = WoosukTheme.colors.Primary100,
                    unfocusedBorderColor = WoosukTheme.colors.Black40,
                ),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "태그",
            style = WoosukTheme.typography.bodyLargeMedium,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.BasicContentPadding),
        )

        OutlinedTextField(
            value = loginInfo.tag,
            placeholder = {
                Text(
                    "KR1",
                    style = WoosukTheme.typography.bodyLargeRegular,
                    color = WoosukTheme.colors.Black40,
                )
            },
            onValueChange = onTagChanged,
            modifier = Modifier.fillMaxWidth(),
            textStyle = WoosukTheme.typography.bodyLargeRegular,
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = WoosukTheme.colors.Primary100,
                    unfocusedBorderColor = WoosukTheme.colors.Black40,
                ),
            leadingIcon = { Icon(imageVector = Icons.Filled.Tag, contentDescription = null) },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onLogin(loginInfo.nickName, loginInfo.tag)
            },
            enabled = loginInfo.canLogin,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = WoosukTheme.colors.Primary100,
                    disabledContainerColor = WoosukTheme.colors.Black20,
                ),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(20.dp),
        ) {
            Text(
                "로그인",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = WoosukTheme.typography.bodyLargeMedium,
            )
        }

        Text(
            "로그인 기록",
            style = WoosukTheme.typography.heading5,
            modifier = Modifier.padding(vertical = WoosukTheme.padding.LargeVerticalPadding),
        )

        LazyColumn {
            items(
                items = recentLoginUsers,
            ) { user ->
                Text(
                    text = user.nickName + "#${user.tag}",
                    style = WoosukTheme.typography.bodyLargeRegular,
                    modifier =
                        Modifier.clickable {
                            onLogin(user.nickName, user.tag)
                        }.padding(vertical = WoosukTheme.padding.BasicContentPadding),
                )
            }
        }
    }
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
            CircularProgressIndicator(
                color = WoosukTheme.colors.Primary100,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
