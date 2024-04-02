import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.RankInfo
import com.woosuk.domain.model.match.RankTier
import com.woosuk.domain.model.match.RankTierStep
import com.woosuk.domain.model.match.RankTierType
import kotlin.test.Test
import kotlin.test.assertEquals

class RankInfoTest {
    @Test
    fun `승률을 계산할 수 있다`() {
        // given
        val rankInfo =
            RankInfo(
                queueType = QueueType.SOLO_RANK,
                rankTier =
                    RankTier(
                        type = RankTierType.BRONZE,
                        step = RankTierStep.TWO,
                        point = 9805,
                    ),
                winCount = 3,
                loseCount = 1,
            )
        // when
        val actual = rankInfo.getWinRate()
        // then
        assertEquals(75.0f, actual)
    }
}
