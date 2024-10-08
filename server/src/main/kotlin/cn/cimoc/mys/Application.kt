package cn.cimoc.mys

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main() {
    CookieStore.cookies.add(LocalCookie().apply {
        cookieToken = "2yoswDmoSYcDmzhYUD3gPCxsroa8WTYrqBcxY2fV"
        accountId = "159956588"
    })
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}


fun Application.module() {

    routing {
        put(URL_SET_COOKIT) {
            CookieStore.cookies.add(LocalCookie().apply {

            })
            call.respondText(Json.encodeToString(Response<String>(200, "OK", null)))
        }

        get(URL_GET_COOKIT) {
            call.respondText(Json.encodeToString(Response<List<LocalCookie>>(200, "OK", CookieStore.cookies)))
        }
    }
}