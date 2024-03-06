package com.woosuk.onboarding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.woosuk.domain.model.LoginInfo

@Composable
fun OnboardingRoute() {
    val loginInfo = LoginInfo("haha","0508")
    Text(loginInfo.nickName, fontSize = 100.sp)
    Text(text = loginInfo.tag, fontSize = 100.sp)
}
