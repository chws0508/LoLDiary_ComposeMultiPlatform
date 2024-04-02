package com.woosuk.network.service

import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.ktor.requestApiResponse
import com.woosuk.network.model.CdnDto
import com.woosuk.network.model.RuneCategoryDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

interface CdnService {
    suspend fun getCdnUrlPrefix(): String

    suspend fun getRunes(rulPrefix: String): List<RuneCategoryDto>
}

class DefaultCdnService(
    private val supabaseClient: SupabaseClient,
) : CdnService {
    override suspend fun getCdnUrlPrefix(): String {
        return supabaseClient.from("cdn").select().decodeSingle<CdnDto>().url
    }

    override suspend fun getRunes(urlPrefix: String): List<RuneCategoryDto> {
        val client = HttpClient()

        val json =
            client
                .requestApiResponse<String>("${urlPrefix}data/en_US/runesReforged.json")
                .getOrNull() ?: ""
        return Json.decodeFromString<List<RuneCategoryDto>>(json)
    }
}
