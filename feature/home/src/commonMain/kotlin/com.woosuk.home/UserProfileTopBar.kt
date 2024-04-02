package com.woosuk.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.model.Account
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserProfileTopBar(
    modifier: Modifier = Modifier,
    account: Account,
    profileImageUrl: String,
    onRefreshClick: () -> Unit,
    level: Int,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = WoosukTheme.colors.Black0),
        modifier = modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ProfileImage(profileImageUrl, level)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        modifier = Modifier.basicMarquee(animationMode = MarqueeAnimationMode.Immediately),
                        text = account.nickName,
                        style = WoosukTheme.typography.heading5,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "#${account.tag}",
                        style = WoosukTheme.typography.bodySmallMedium,
                        color = WoosukTheme.colors.Black40,
                    )
                }
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "전적 새로고침",
                modifier =
                    Modifier.clickable {
                        onRefreshClick()
                    }.padding(horizontal = WoosukTheme.padding.BasicHorizontalPadding),
                tint = WoosukTheme.colors.Primary100,
            )
        },
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}

@Composable
fun ProfileImage(
    profileImageUrl: String,
    level: Int,
) {
    Box {
        KamelImage(
            resource = asyncPainterResource(profileImageUrl),
            contentDescription = profileImageUrl,
            modifier = Modifier.size(44.dp).clip(CircleShape),
        )
        Box(
            Modifier.align(Alignment.BottomCenter)
                .background(
                    color = WoosukTheme.colors.Black100,
                    shape = RoundedCornerShape(10.dp),
                ),
        ) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = level.toString(),
                style = WoosukTheme.typography.bodyExtraSmallRegular,
                color = WoosukTheme.colors.Black0,
            )
        }
    }
}
