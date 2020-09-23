package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.User
import dev.mirror.kt.kaguya.domain.repository.UserRepository
import dev.mirror.kt.kaguya.infra.model.UserEntity
import dev.mirror.kt.kaguya.infra.toDomain
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepositoryImpl : UserRepository {
    override fun find(id: UUID): User? {
        return transaction {
            UserEntity.findById(id)
                ?.toDomain()
        }
    }

    override fun register(name: String, iconUrl: String?): User {
        return transaction {
            UserEntity.new {
                this.name = name
                icon = iconUrl
            }.toDomain()
        }
    }
}
