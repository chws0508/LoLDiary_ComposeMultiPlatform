package com.woosuk.domain.usecase

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.match.MatchDetails
import com.woosuk.domain.repository.AccountRepository
import com.woosuk.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMatchDetailsUseCase(
    private val matchRepository: MatchRepository,
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(
        matchId: String,
        onError: suspend (ErrorState) -> Unit,
    ): Flow<MatchDetails> {
        val puuid =
            accountRepository.getCurrentAccount()?.puuid
                ?: return flow { onError(ErrorState(ErrorState.ExceptionCode, null)) }
        return matchRepository.getUserMatchDetail(matchId, puuid, onError)
    }
}
