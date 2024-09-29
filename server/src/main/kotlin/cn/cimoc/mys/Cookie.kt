package cn.cimoc.mys

import io.ktor.http.*
import kotlinx.serialization.Serializable

/**
 * @author <a href="https://github.com/Sagiri-kawaii01">lgz</a>
 * @date 2024/9/29 15:46
 * @since
 */
@Serializable
class LocalCookie {
    var accountId: String? = null
    var cookieToken: String? = null

    fun cookie(): List<Cookie> {
        return listOf(
            Cookie("account_id", accountId ?: ""),
            Cookie("cookie_token", cookieToken ?: ""),
        )
    }
}

object CookieStore {
    val cookies = mutableListOf<LocalCookie>()
    var selected = -1

    fun setCookie(newCookies: List<Cookie>) {
        newCookies.forEach {
            when (it.name) {
                "account_id" -> cookies[selected].accountId = it.value
                "cookie_token" -> cookies[selected].cookieToken = it.value
            }
        }
    }
}