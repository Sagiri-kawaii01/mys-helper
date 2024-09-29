package cn.cimoc.mys

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform