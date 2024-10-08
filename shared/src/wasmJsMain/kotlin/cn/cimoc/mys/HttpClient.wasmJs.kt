package cn.cimoc.mys

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

actual fun getHttpClient(): HttpClient {
    return HttpClient() {
        install(ContentNegotiation) {
            @OptIn(ExperimentalSerializationApi::class)
            json(Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false })
        }
    }
}
