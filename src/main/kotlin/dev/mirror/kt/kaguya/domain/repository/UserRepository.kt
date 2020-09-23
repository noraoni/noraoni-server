package dev.mirror.kt.kaguya.domain.repository

import dev.mirror.kt.kaguya.domain.User
import java.util.*

interface UserRepository  {
    fun register(name: String, iconUrl: String?): User
    fun find(id: UUID): User?
}
