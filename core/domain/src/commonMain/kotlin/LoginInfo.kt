data class LoginInfo(
    val nickName: String,
    val tag: String
) {
    val isNickNameBlank: Boolean
        get() = nickName.isBlank()

    val isTagBlank: Boolean
        get() = tag.isBlank()

    val canLogin = !isTagBlank && !isNickNameBlank
}
