package com.baka3k.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val hiDispatcher: HiDispatchers)

enum class HiDispatchers {
    IO,
    SERIAL,
}