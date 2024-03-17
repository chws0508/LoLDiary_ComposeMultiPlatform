package com.woosuk.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.woosuk.domain.model.RankInfo
import com.woosuk.domain.model.RankTierType
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun RankInfo.tierImage(): Painter {
    return when (rankTier.type) {
        RankTierType.IRON -> painterResource(MR.images.tier_iron)
        RankTierType.BRONZE -> painterResource(MR.images.tier_bronze)
        RankTierType.SILVER -> painterResource(MR.images.tier_silver)
        RankTierType.GOLD -> painterResource(MR.images.tier_gold)
        RankTierType.PLATINUM -> painterResource(MR.images.tier_platinum)
        RankTierType.EMERALD -> painterResource(MR.images.tier_emerald)
        RankTierType.DIAMOND -> painterResource(MR.images.tier_diamond)
        RankTierType.MASTER -> painterResource(MR.images.tier_master)
        RankTierType.GRANDMASTER -> painterResource(MR.images.tier_grandmaster)
        RankTierType.CHALLENGER -> painterResource(MR.images.tier_challenger)
    }
}
