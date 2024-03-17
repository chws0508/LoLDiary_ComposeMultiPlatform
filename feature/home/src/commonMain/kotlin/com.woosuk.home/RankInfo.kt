package com.woosuk.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.designsystem.ui.WoosukCard
import com.woosuk.domain.model.RankInfo
import com.woosuk.ui.tierImage

@Composable
fun RankInfo(
    modifier: Modifier = Modifier,
    rankInfo: RankInfo,
) {
    WoosukCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(64.dp).padding(start = 16.dp),
                painter = rankInfo.tierImage(),
                contentDescription = rankInfo.rankTier.type.toString(),
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${rankInfo.rankTier.type} ${rankInfo.rankTier.step.value}",
                    style = WoosukTheme.typography.heading5,
                )
                Text(
                    text = "${rankInfo.rankTier.point}LP",
                    style = WoosukTheme.typography.bodySmallMedium,
                    color = WoosukTheme.colors.Black60,
                )
                Text(
                    text = "${rankInfo.winCount}승 ${rankInfo.loseCount}패 (${rankInfo.getWinRate()}%)",
                    style = WoosukTheme.typography.bodySmallMedium,
                    color = WoosukTheme.colors.Black60,
                )
            }
        }
    }
}
