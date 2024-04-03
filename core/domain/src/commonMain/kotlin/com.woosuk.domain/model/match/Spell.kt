package com.woosuk.domain.model.match

import com.woosuk.domain.util.UrlUtil

enum class Spell(val id: Int) {
    SummonerBarrier(21),
    SummonerBoost(1),
    SummonerCherryFlash(2202),
    SummonerCherryHold(2201),
    SummonerDot(14),
    SummonerExhaust(3),
    SummonerFlash(4),
    SummonerHaste(6),
    SummonerHeal(7),
    SummonerMana(13),
    SummonerPoroRecall(30),
    SummonerPoroThrow(31),
    SummonerSmite(11),
    SummonerSnowURFSnowball_Mark(39),
    SummonerSnowball(32),
    SummonerTeleport(12),
    Summoner_UltBookPlaceholder(54),
    Summoner_UltBookSmitePlaceholder(5),
    ;

    private val spellImagePrefix =
        "img/spell/"
    private val imageType = ".png"

    val imageUrl = UrlUtil.cdnPrefix + spellImagePrefix + name + imageType

    companion object {
        fun fromId(id: Int): Spell {
            return values().find { it.id == id } ?: Summoner_UltBookPlaceholder
        }
    }
}
