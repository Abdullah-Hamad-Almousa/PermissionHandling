package com.mysticbyte.permissionhandling

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform