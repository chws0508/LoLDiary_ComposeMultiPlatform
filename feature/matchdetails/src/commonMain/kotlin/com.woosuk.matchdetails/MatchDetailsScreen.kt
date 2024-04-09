package com.woosuk.matchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.designsystem.ui.BackButtonTopAppBar
import com.woosuk.domain.model.date.Date
import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.TeamColor
import com.woosuk.domain.model.match.TeamMatches
import com.woosuk.navigation.NavigationBarInvisibleScreen
import com.woosuk.ui.ChampionRuneSpellSection
import com.woosuk.ui.ItemSection
import com.woosuk.ui.getName
import com.woosuk.ui.roundToDecimals
import com.woosuk.ui.toMinuteAndHour
import com.woosuk.ui.toRelativeString
import kotlinx.coroutines.flow.collectLatest

class MatchDetailsScreen(
    private val matchId: String,
) : NavigationBarInvisibleScreen() {
    @Composable
    override fun ScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarController = LocalSnackbarController.current
        val screenModel = getScreenModel<MatchDetailsScreenModel>()
        val matchDetailsUiState by screenModel.matchDetailsUiState.collectAsState()

        LaunchedEffect(null) {
            screenModel.initMatchId(matchId)
            screenModel.uiEvent.collectLatest {
                when (it) {
                    MatchDetailsUiEvent.NetworkError -> snackbarController.showMessage("데이터를 불러올 수 없어요")
                }
            }
        }

        MatchDetailsContent(
            matchDetailsUiState = matchDetailsUiState,
            onClickBackButton = { navigator.pop() },
        )
    }
}

@Composable
fun MatchDetailsContent(
    onClickBackButton: () -> Unit,
    matchDetailsUiState: MatchDetailsUiState,
) {
    when (matchDetailsUiState) {
        MatchDetailsUiState.FailByNetwork -> {
            Box(modifier = Modifier.fillMaxSize()) {
                BackButtonTopAppBar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    onClickBackButton = onClickBackButton,
                    title = {},
                )
                Text(
                    "네트워크를 확인해주세요",
                    style = WoosukTheme.typography.heading5,
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
                )
            }
        }

        MatchDetailsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is MatchDetailsUiState.Success -> {
            val matchDetails = matchDetailsUiState.data
            Column {
                MatchDetailsTopBar(
                    onClickBackButton = onClickBackButton,
                    queueType = matchDetails.gameInfo.queueType,
                    endAt = matchDetails.gameInfo.endAt,
                    totalPlayTime = matchDetails.gameInfo.totalPlayTime,
                    isWin = matchDetails.currentUserMatch.userStats.isWin,
                )
                HorizontalDivider()
                Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
                    TeamInfo(
                        teamMatches = matchDetails.currentUserTeamMatches,
                        currentUserPuuid = matchDetails.currentUserPuuid,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TeamInfo(
                        teamMatches = matchDetails.otherTeamMatches,
                        currentUserPuuid = matchDetails.currentUserPuuid,
                    )
                }
            }
        }
    }
}

@Composable
fun TeamInfo(
    modifier: Modifier = Modifier,
    teamMatches: TeamMatches,
    currentUserPuuid: String,
) {
    val mainColor =
        if (teamMatches.isWin) WoosukTheme.colors.Primary80 else WoosukTheme.colors.Error

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(color = mainColor)
        Row(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(horizontal = WoosukTheme.padding.BasicHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = if (teamMatches.isWin) "승" else "패배",
                style = WoosukTheme.typography.bodySmallMedium,
                color = mainColor,
            )
            Text(
                text = if (teamMatches.team == TeamColor.Blue) "(블루)" else "(레드)",
                style = WoosukTheme.typography.bodySmallMedium,
                color = WoosukTheme.colors.Black60,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "${teamMatches.totalKill} / ",
                style = WoosukTheme.typography.bodySmallMedium,
            )
            Text(
                text = teamMatches.totalDeath.toString(),
                style = WoosukTheme.typography.bodySmallMedium,
                color = WoosukTheme.colors.Error,
            )
            Text(
                text = " / ${teamMatches.totalAssist}",
                style = WoosukTheme.typography.bodySmallMedium,
            )
        }
        HorizontalDivider()
        teamMatches.matches.forEach { userMatchInfo ->
            Row(
                modifier =
                    Modifier.background(
                        color = if (userMatchInfo.account.puuid == currentUserPuuid) WoosukTheme.colors.Primary20 else WoosukTheme.colors.Black0,
                    ).fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    ChampionRuneSpellSection(
                        modifier = Modifier.padding(start = 16.dp),
                        champion = userMatchInfo.champion,
                        runes = userMatchInfo.runes,
                        spells = userMatchInfo.spells,
                        championImageSize = 36,
                        runeAndSpellSize = 18,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row {
                            Text(
                                text = userMatchInfo.account.nickName,
                                style = WoosukTheme.typography.bodyMediumMedium,
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "#${userMatchInfo.account.tag}",
                                style = WoosukTheme.typography.bodySmallRegular,
                                color = WoosukTheme.colors.Black60,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(
                                text = "${userMatchInfo.userStats.kill} / ",
                                style = WoosukTheme.typography.bodySmallMedium,
                            )
                            Text(
                                text = "${userMatchInfo.userStats.death}",
                                style = WoosukTheme.typography.bodySmallMedium,
                                color = WoosukTheme.colors.Error,
                            )
                            Text(
                                text = " / ${userMatchInfo.userStats.assist} / ",
                                style = WoosukTheme.typography.bodySmallMedium,
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${userMatchInfo.userStats.kdaScore.roundToDecimals(2)}",
                                style = WoosukTheme.typography.bodySmallMedium,
                                color = WoosukTheme.colors.Warning,
                            )
                        }
                    }
                }
                ItemSection(
                    modifier = Modifier.padding(end = 16.dp),
                    itemList = userMatchInfo.items,
                    isWin = userMatchInfo.userStats.isWin,
                )
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun MatchDetailsTopBar(
    onClickBackButton: () -> Unit,
    queueType: QueueType,
    endAt: Date,
    totalPlayTime: Long,
    isWin: Boolean,
) {
    BackButtonTopAppBar(
        onClickBackButton = onClickBackButton,
        title = {
            val text = if (isWin) "승리" else "패배"
            Text(
                text = text,
                style = WoosukTheme.typography.heading5,
            )
        },
        actions = {
            Row(
                modifier =
                    Modifier.padding(
                        end = 16.dp,
                    ).height(IntrinsicSize.Min),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = queueType.getName(),
                    style = WoosukTheme.typography.bodySmallRegular,
                    color = WoosukTheme.colors.Black100,
                )
                VerticalDivider(Modifier.fillMaxHeight().padding(horizontal = 7.dp))
                Text(
                    endAt.toRelativeString(),
                    style = WoosukTheme.typography.bodySmallRegular,
                    color = WoosukTheme.colors.Black100,
                )
                VerticalDivider(Modifier.fillMaxHeight().padding(horizontal = 7.dp))
                Text(
                    totalPlayTime.toMinuteAndHour(),
                    style = WoosukTheme.typography.bodySmallRegular,
                    color = WoosukTheme.colors.Black100,
                )
            }
        },
    )
}
