package com.woosuk.domain.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.UserMatchInfo
import com.woosuk.domain.repository.MatchRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDateTime

class UserMatchInfoPagingSource(
    private val matchRepository: MatchRepository,
    private val loadSize: Int,
    private val queueType: QueueType?,
    private val startTime: LocalDateTime?,
    private val endTime: LocalDateTime?,
    private val puuid: String,
    private val onError: (ErrorState) -> Unit,
) : PagingSource<Int, UserMatchInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserMatchInfo> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        var errorState: ErrorState? = null
        val data =
            matchRepository.getUserMatchInfos(
                offset = currentPage * loadSize,
                loadSize = loadSize,
                queueId = queueType?.id,
                startTime = startTime,
                entTime = endTime,
                puuid = puuid,
                onError = {
                    onError(it)
                    errorState = it
                },
            ).firstOrNull() ?: emptyList()
        val endOfPaginationReached = data.isEmpty()
        val prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
        val nextKey =
            if (endOfPaginationReached) null else currentPage + (params.loadSize / loadSize)
        return if (errorState == null) {
            LoadResult.Page(data, prevKey, nextKey)
        } else {
            LoadResult.Error(IllegalStateException(errorState!!.errorMessage))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserMatchInfo>): Int? {
        return state.anchorPosition?.let { achorPosition ->
            state.closestPageToPosition(achorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 0
    }
}
