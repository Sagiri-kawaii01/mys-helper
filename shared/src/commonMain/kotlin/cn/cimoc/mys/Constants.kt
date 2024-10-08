package cn.cimoc.mys

const val SERVER_PORT = 8081
const val URL_SET_COOKIT = "/setCookie"
const val URL_GET_COOKIT = "/getCookie"

fun url(uri: String): String {
    return "http://127.0.0.1:$SERVER_PORT/${uri.removePrefix("/")}"
}
