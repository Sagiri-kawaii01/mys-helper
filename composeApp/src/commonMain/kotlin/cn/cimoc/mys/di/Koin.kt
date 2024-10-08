package cn.cimoc.mys.di

import cn.cimoc.mys.model.TestViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun appModule() = module {
    single { TestViewModel() }
}

fun initKoin() {
    startKoin {
        koinApplication()
        modules(appModule())
    }
}