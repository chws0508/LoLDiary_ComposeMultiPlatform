package com.woosuk.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.designsystem.ui.WoosukCard
import com.woosuk.domain.model.match.RankInfo
import com.woosuk.ui.roundToDecimals
import com.woosuk.ui.tierImage

@Composable
fun RankInfoList(
    modifier: Modifier = Modifier,
    rankInfoList: List<RankInfo?>,
) {
    LazyRow {
        itemsIndexed(
            items = rankInfoList,
        ) { index, rankInfo ->
            WoosukCard(modifier = modifier) {
                Column {
                    val rankTypeText = if (index == 0) "개인/2인 랭크" else "자유 랭크"
                    Box(
                        modifier =
                            Modifier.background(
                                color = WoosukTheme.colors.Primary40,
                                shape = RoundedCornerShape(5.dp),
                            ),
                    ) {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = rankTypeText,
                            style = WoosukTheme.typography.bodyMediumMedium,
                            color = WoosukTheme.colors.Black100,
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(64.dp).padding(start = 16.dp),
                            painter = rankInfo.tierImage(),
                            contentDescription = "랭크 티어 이미지",
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (rankInfo == null) "UnRanked" else "${rankInfo.rankTier.type} ${rankInfo.rankTier.step.value}",
                                style = WoosukTheme.typography.heading5,
                            )
                            Text(
                                text = "${rankInfo?.rankTier?.point ?: 0}LP",
                                style = WoosukTheme.typography.bodySmallMedium,
                                color = WoosukTheme.colors.Black60,
                            )
                            Text(
                                text = "${rankInfo?.winCount ?: 0}승 ${rankInfo?.loseCount ?: 0}패 (${
                                    rankInfo?.getWinRate()?.roundToDecimals(2) ?: 0
                                }%)",
                                style = WoosukTheme.typography.bodySmallMedium,
                                color = WoosukTheme.colors.Black60,
                            )
                        }
                    }
                }
            }
        }
    }
}
