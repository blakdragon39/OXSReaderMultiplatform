package com.blakdragon.oxsreadermultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform