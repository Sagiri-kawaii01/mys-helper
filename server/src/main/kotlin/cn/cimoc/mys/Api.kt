package cn.cimoc.mys

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.lang.RuntimeException

/**
 * @author <a href="https://github.com/Sagiri-kawaii01">lgz</a>
 * @date 2024/9/29 14:14
 * @since
 */

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false  })
    }
}

const val URL_ACCOUNT_ADDRESS_LIST = "https://api-takumi.mihoyogift.com/account/address/list"
const val URL_MYB = "https://api-takumi.miyoushe.com/common/homutreasure/v1/web/user/point?app_id=1&point_sn=myb"
const val URL_GOOD_LIST = "https://api-takumi.miyoushe.com/common/homushop/v1/web/goods/list?app_id=1&page=1&page_size=20&point_sn=myb&game="

val ADDRESS_HEADERS = mapOf(
    "Accept" to "*/*",
    "Accept-Encoding" to "gzip, deflate, br, zstd",
    "Connection" to "keep-alive",
    "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.142.86 Safari/537.36",
    "Origin" to "https://user.mihoyogift.com",
    "Referer" to "https://user.mihoyogift.com/"
)

class ApiHandler<T>(
    val response: Response<T>
) {

    fun success(): Boolean {
        return response.retCode == 0 || response.message in listOf("成功", "OK")
    }

    fun loginExpired(): Boolean {
        return response.retCode in listOf(-100, 10001) || response.message == "登录失效，请重新登录"
    }
}

@Throws(ApiException::class)
suspend fun <T> invoke(block: suspend () -> ApiHandler<T>): T {
    if (CookieStore.selected == -1) {
        throw ApiException("未选中账号，请先登录")
    }
    val res = block()
    if (!res.success()) {
        if (res.loginExpired()) {
            CookieStore.cookies.removeAt(CookieStore.selected)
            CookieStore.selected = -1
            throw ApiException("登录已过期，请重新登录")
        } else {
            throw ApiException(res.response.message)
        }
    }
    if (null == res.response.data) {
        throw ApiException("未获取到数据")
    }
    return res.response.data
}

suspend fun getAddress(): ApiHandler<ListData<Address>> {
    val res = client.get(URL_ACCOUNT_ADDRESS_LIST) {
        setHeader()
    }
    return res.result()
}

suspend fun getMyb(): ApiHandler<Coins> {
    val res = client.get(URL_MYB) {
        setHeader()
    }
    return res.result()
}

suspend fun getGames(): ApiHandler<GoodList> {
    val res = client.get("${URL_GOOD_LIST}bh") {
        setHeader()
    }
    return res.result()
}

suspend fun getGoods(game: Game): ApiHandler<GoodList> {
    val res = client.get("${URL_GOOD_LIST}${game.key}") {
        setHeader()
    }
    return res.result()
}

class ApiException(override val message: String): RuntimeException()

private suspend inline fun <reified T> HttpResponse.result(): ApiHandler<T> {
    CookieStore.setCookie(this.setCookie())
    return ApiHandler(this.body())
}

private fun HttpRequestBuilder.setHeader() {
    CookieStore.cookies[CookieStore.selected].cookie().forEach {
        this.cookie(it.name, it.value)
    }
    this.contentType(ContentType.Application.Json)
}

@Serializable
data class Response<T>(
    @SerialName("retcode")
    val retCode: Int,
    val message: String,
    val data: T?
)

@Serializable
data class ListData<T>(
    val list: List<T>
)

@Serializable
data class Address(
    val id: String,
    @SerialName("connect_name")
    val connectName: String,
    @SerialName("connect_areacode")
    val connectAreaCode: String,
    @SerialName("connect_mobile")
    val connectMobile: String,
    val country: Int,
    val province: Int,
    val city: Int,
    val county: Int,
    @SerialName("province_name")
    val provinceName: String,
    @SerialName("city_name")
    val cityName: String,
    @SerialName("county_name")
    val countyName: String,
    @SerialName("addr_ext")
    val addrExt: String,
    @SerialName("is_default")
    val isDefault: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("is_crypto")
    val isCrypto: Boolean
)

@Serializable
data class Coins(
    val points: String,
    @SerialName("direct_shop")
    val directShop: Boolean
)

@Serializable
data class GoodList(
    val list: List<Good>,
    val total: Int,
    val games: List<Game>
)

@Serializable
data class Good(
    @SerialName("app_id")
    val appId: Int,
    @SerialName("goods_id")
    val goodsId: String,
    @SerialName("goods_name")
    val goodsName: String,
    val type: Int,
    val price: Int,
    @SerialName("point_sn")
    val pointSn: String,
    val icon: String,
    @SerialName("unlimit")
    val unLimit: Boolean,
    val total: Int,
    @SerialName("account_cycle_type")
    val accountCycleType: String,
    @SerialName("account_cycle_limit")
    val accountCycleLimit: Int,
    @SerialName("account_cycle_num")
    val accountExchangeNum: Int,
    @SerialName("role_cycle_type")
    val roleCycleType: String,
    @SerialName("role_cycle_limit")
    val roleCycleLimit: Int,
    @SerialName("role_cycle_num")
    val roleExchangeNum: Int,
    val start: String,
    val end: String,
    val status: String,
    @SerialName("next_time")
    val nextTime: Int,
    @SerialName("next_num")
    val nextNum: Int,
    @SerialName("now_time")
    val nowTime: Int
)

@Serializable
data class Game(
    val name: String,
    val key: String
)