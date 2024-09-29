package cn.cimoc.mys

import kotlinx.coroutines.runBlocking

/**
 * @author <a href="https://github.com/Sagiri-kawaii01">lgz</a>
 * @date 2024/9/29 10:14
 * @since
 */

fun main() {
    CookieStore.cookies.add(LocalCookie().apply {
        cookieToken = "2yoswDmoSYcDmzhYUD3gPCxsroa8WTYrqBcxY2fV"
        accountId = "159956588"
    })
    CookieStore.selected = 0
    runBlocking {
        val data = invoke(::getGames)
        println(data)
    }
}
