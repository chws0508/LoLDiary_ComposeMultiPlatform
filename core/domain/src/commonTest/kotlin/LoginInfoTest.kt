import com.woosuk.domain.model.LoginInfo
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LoginInfoTest {

    @Test
    fun `닉네임이 비어있다면 로그인이 불가능하다`() {
        // given
        val loginInfo = LoginInfo("", "0508")
        // when
        val actual = loginInfo.canLogin
        // then
        assertFalse { actual }
    }

    @Test
    fun `태그가 비어있다면 로그인이 불가능하다`() {
        // given
        val loginInfo = LoginInfo("박보영", "")
        // when
        val actual = loginInfo.canLogin
        // then
        assertFalse { actual }
    }

    @Test
    fun `닉네임과 태그 모두 비어있지 않다면 로그인이 가능하다`() {
        // given
        val loginInfo = LoginInfo("박보영", "0508")
        // when
        val actual = loginInfo.canLogin
        // then
        assertTrue { actual }
    }
}
