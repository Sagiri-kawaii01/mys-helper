package cn.cimoc.mys

import io.ktor.client.*

actual fun getHttpClient(): HttpClient {
    return HttpClient()
}

