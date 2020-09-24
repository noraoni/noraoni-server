package dev.mirror.kt.kaguya.domain.service

import dev.mirror.kt.kaguya.domain.User
import dev.mirror.kt.kaguya.domain.repository.UserRepository
import java.util.*

class UserService(
    private val repository: UserRepository
) {
    fun register(name: String, iconUrl: String?): User? {
        return repository.register(name, iconUrl)
    }

    fun find(id: UUID): User? = repository.find(id)
}
