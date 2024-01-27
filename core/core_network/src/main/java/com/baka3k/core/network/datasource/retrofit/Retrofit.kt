package com.baka3k.core.network.datasource.retrofit

import kotlinx.serialization.Serializable


@Serializable
data class NetworkResponse<T>(
    val data: T
)