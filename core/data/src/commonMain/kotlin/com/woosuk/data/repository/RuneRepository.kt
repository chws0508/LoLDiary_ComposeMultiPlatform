package com.woosuk.data.repository

import com.woosuk.domain.model.match.Rune
import com.woosuk.network.model.RuneCategoryDto
import com.woosuk.network.service.CdnService
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RuneRepository : KoinComponent {
    val cdnService: CdnService by inject()

    private val _runeInfo = MutableStateFlow(listOf<RuneCategoryDto>())
    val runeInfo = _runeInfo.asStateFlow()

    fun updateRunes(urlPrefix: String) =
        flow {
            _runeInfo.update { cdnService.getRunes(urlPrefix) }
            emit(Unit)
        }.flowOn(Dispatchers.IO)

    fun getRune(id: Int): Rune {
        Napier.v(id.toString(), tag = "룬 아이디")
        val list = runeInfo.value
        list.forEach {
            if (it.id == id) {
                return Rune(it.id, it.icon)
            }
        }
        val slots = list.flatMap { it.slots }
        val runes = slots.flatMap { it.runes }
        val rune = runes.find { it.id == id } ?: return Rune(0, "")
        return Rune(rune.id, rune.icon)
    }
}
