package com.woosuk.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.designsystem.ui.noRippleClickable
import com.woosuk.domain.model.User
import com.woosuk.navigation.SharedScreen
import com.woosuk.navigation.getRootNavigator
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

class SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "세팅"
            val icon = rememberVectorPainter(Icons.Default.Settings)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SettingScreenModel>()
        val userUiState by viewModel.user.collectAsState()
        val rootNavigator = getRootNavigator()
        val onboardingScreen = rememberScreen(SharedScreen.OnboardingScreen)
        val snackbarController = LocalSnackbarController.current
        LaunchedEffect(null) {
            viewModel.logoutSuccess.collect { success ->
                if (success) {
                    snackbarController.showMessage("로그아웃에 성공했습니다")
                    rootNavigator.replaceAll(onboardingScreen)
                } else {
                    snackbarController.showMessage("로그아웃에 실패했습니다")
                }
            }
        }
        when (userUiState) {
            SettingUiState.Fail -> {
            }

            SettingUiState.Loading -> {
            }

            is SettingUiState.Success -> {
                val user = (userUiState as SettingUiState.Success).user
                Column {
                    SettingTopAppBar()
                    Spacer(modifier = Modifier.height(8.dp))
                    SettingProfile(
                        modifier = Modifier.padding(horizontal = WoosukTheme.padding.BasicHorizontalPadding),
                        user = user,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier =
                            Modifier.background(WoosukTheme.colors.Black20)
                                .fillMaxWidth()
                                .height(4.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "로그아웃",
                        style = WoosukTheme.typography.bodyLargeMedium,
                        modifier =
                            Modifier.noRippleClickable {
                                viewModel.logOut()
                            }.padding(horizontal = WoosukTheme.padding.BasicHorizontalPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun SettingProfile(
    modifier: Modifier = Modifier,
    user: User,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        KamelImage(
            resource = asyncPainterResource(user.profileImageUrl),
            contentDescription = "프로필 이미지",
            modifier = Modifier.clip(CircleShape).size(54.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = user.account.nickName,
                style = WoosukTheme.typography.bodyLargeMedium,
            )
            Text(
                text = "#${user.account.tag}",
                style = WoosukTheme.typography.bodySmallRegular,
                color = WoosukTheme.colors.Black60,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = WoosukTheme.colors.Black0),
        modifier = modifier,
        title = {
            Text("설정", style = WoosukTheme.typography.heading5, fontWeight = FontWeight.ExtraBold)
        },
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}
