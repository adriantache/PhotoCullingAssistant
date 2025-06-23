package com.adriantache.photoculling

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform