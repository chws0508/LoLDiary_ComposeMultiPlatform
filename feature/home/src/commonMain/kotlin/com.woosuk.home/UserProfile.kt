package com.woosuk.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.designsystem.ui.WoosukCard
import com.woosuk.domain.model.Account
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    account: Account,
    profileImageUrl: String,
    level: Int,
) {
    WoosukCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.padding(16.dp).weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box {
                    KamelImage(
                        resource = asyncPainterResource(profileImageUrl),
                        contentDescription = profileImageUrl,
                        modifier = Modifier.size(48.dp).clip(CircleShape),
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
                            style = WoosukTheme.typography.bodySmallRegular,
                            color = WoosukTheme.colors.Black0,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
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
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("오늘의 전적", style = WoosukTheme.typography.bodyDoubleExtraLageMedium)
                Text(
                    text = "3승 2패",
                    style = WoosukTheme.typography.bodyLargeRegular,
                    color = WoosukTheme.colors.Success,
                )
            }
        }
    }
}
