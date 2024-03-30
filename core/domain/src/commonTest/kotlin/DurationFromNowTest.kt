import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlin.test.Test
import kotlin.test.assertEquals

class DurationFromNowTest {
    @Test
    fun `두 시간차를 계산할 수 있다`() {
        // given
        val beforeDate =
            LocalDateTime(
                date =
                    LocalDate(
                        2024,
                        8,
                        6,
                    ),
                time =
                    LocalTime(
                        12,
                        5,
                        1,
                    ),
            )
        val afterDate =
            LocalDateTime(
                date =
                    LocalDate(
                        2024,
                        8,
                        6,
                    ),
                time =
                    LocalTime(
                        12,
                        5,
                        10,
                    ),
            )
        // when
        val duration =
            beforeDate.toInstant(TimeZone.currentSystemDefault()).periodUntil(
                afterDate.toInstant(TimeZone.currentSystemDefault()),
                TimeZone.currentSystemDefault(),
            )
        assertEquals(9, duration.seconds)
    }
}
