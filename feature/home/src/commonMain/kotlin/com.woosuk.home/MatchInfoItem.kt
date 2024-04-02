package com.woosuk.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.model.date.Date
import com.woosuk.domain.model.match.Champion
import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.Rune
import com.woosuk.domain.model.match.Spell
import com.woosuk.domain.model.match.UserMatchInfo
import com.woosuk.ui.getName
import com.woosuk.ui.toMinuteAndHour
import com.woosuk.ui.toRelativeString
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun MatchInfoItem(
    modifier: Modifier = Modifier,
    userMatchInfo: UserMatchInfo,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors =
            CardDefaults.cardColors(
                containerColor = if (userMatchInfo.gameInfo.isWin) WoosukTheme.colors.Primary40 else WoosukTheme.colors.Secondary60,
            ),
    ) {
        Column {
            MatchInfoTopSection(
                isWin = userMatchInfo.gameInfo.isWin,
                queueType = userMatchInfo.gameInfo.queueType,
                totalPlayTime = userMatchInfo.gameInfo.totalPlayTime,
                endTimeAt = userMatchInfo.gameInfo.endAt,
            )
            HorizontalDivider(color = WoosukTheme.colors.Black60)
            Row(verticalAlignment = Alignment.CenterVertically) {
                ChampionRuneSpellSection(
                    champion = userMatchInfo.champion,
                    runes = userMatchInfo.runes,
                    spells = userMatchInfo.spells,
                )
            }
        }
    }
}

@Composable
fun ChampionRuneSpellSection(
    modifier: Modifier = Modifier,
    champion: Champion,
    runes: List<Rune>,
    spells: List<Spell>,
) {
    Row {
        KamelImage(
            resource = asyncPainterResource(champion.imageUrl),
            contentDescription = "챔피언",
            modifier = modifier.size(48.dp).clip(CircleShape),
        )
        Column {
            KamelImage(
                resource = asyncPainterResource(spells[0].imageUrl),
                contentDescription = "첫번쨰 스펠",
                modifier = modifier.size(24.dp).clip(CircleShape),
                onFailure = { Box(modifier = Modifier) { Text("실패") } },
            )
            KamelImage(
                resource = asyncPainterResource(spells[1].imageUrl),
                contentDescription = "두번쨰 스펠",
                modifier = modifier.size(24.dp).clip(CircleShape),
                onFailure = { Box(modifier = Modifier) { Text("실패") } },
            )
        }
        Column {
            KamelImage(
                resource = asyncPainterResource(runes[0].imageUrl),
                contentDescription = "첫번째 룬",
                modifier = modifier.size(24.dp).clip(CircleShape),
            )
            KamelImage(
                resource = asyncPainterResource(runes[1].imageUrl),
                contentDescription = "두번째 룬",
                modifier = modifier.size(24.dp).clip(CircleShape),
            )
        }
    }
}

@Composable
fun MatchInfoTopSection(
    modifier: Modifier = Modifier,
    isWin: Boolean,
    queueType: QueueType,
    totalPlayTime: Long,
    endTimeAt: Date,
) {
    Row(
        modifier = modifier.padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier =
                Modifier.weight(1f).padding(
                    horizontal = WoosukTheme.padding.BasicHorizontalPadding,
                ),
        ) {
            val resultText = if (isWin) "승리" else "패배"
            val color =
                if (isWin) {
                    WoosukTheme.colors.Success
                } else {
                    WoosukTheme.colors.Error
                }
            Text(
                text = resultText,
                style = WoosukTheme.typography.bodyMediumMedium,
                color = color,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = totalPlayTime.toMinuteAndHour(),
                style = WoosukTheme.typography.bodySmallRegular,
                color = WoosukTheme.colors.Black60,
            )
        }
        Row(
            modifier =
                Modifier.padding(
                    horizontal = WoosukTheme.padding.BasicHorizontalPadding,
                ).height(IntrinsicSize.Min),
        ) {
            Text(
                text = queueType.getName(),
                style = WoosukTheme.typography.bodySmallRegular,
                color = WoosukTheme.colors.Black60,
            )
            VerticalDivider(Modifier.fillMaxHeight().padding(horizontal = 7.dp))
            Text(
                endTimeAt.toRelativeString(),
                style = WoosukTheme.typography.bodySmallRegular,
                color = WoosukTheme.colors.Black60,
            )
        }
    }
}
