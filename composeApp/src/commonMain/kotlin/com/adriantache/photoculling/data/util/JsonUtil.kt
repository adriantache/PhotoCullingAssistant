package com.adriantache.photoculling.data.util

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json


inline fun <reified T> String?.decodeJson(json: Json = Json): T? {
    if (this == null) return null

    return try {
        json.decodeFromString<T>(this)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    } catch (e: SerializationException) {
        e.printStackTrace()
        null
    }
}
