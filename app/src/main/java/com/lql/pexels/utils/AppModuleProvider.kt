package com.lql.pexels.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.text.SimpleDateFormat
import java.util.*

object AppModuleProvider {
    val objectMapper: ObjectMapper
        get() = jacksonObjectMapper()
            .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH))
            .enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

    private val apiConfig: ApiConfig = ApiConfig()
    val apiBaseUrl: String = "${apiConfig.domain}/v${apiConfig.apiVersion}/"
}

data class ApiConfig(
    val domain: String = "https://api.pexels.com",//use the Pexels API

    val apiVersion: String = "1"
)
//API request:
//https://api.pexels.com/v1/search?query=YOUR_QUERY&per_page=30&page=1