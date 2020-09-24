package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.User
import dev.mirror.kt.kaguya.domain.repository.UserRepository
import dev.mirror.kt.kaguya.infra.table.Users
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepositoryImpl : UserRepository {
    override fun find(id: UUID): User? {
        return transaction {
            Users.select { Users.id eq id }.singleOrNull()
                ?.let {
                    User(it[Users.id], it[Users.name], it[Users.icon])
                }
        }
    }

    override fun register(name: String, iconUrl: String?): User {
        val user = transaction {
            addLogger(StdOutSqlLogger)
            println("in transaction")
            Users.insert {
                it[id] = UUID.randomUUID()
                it[Users.name] = name
                it[icon] = iconUrl
            }
        }.also { println(it) }

        println("conv to domain obj")
        return User(user[Users.id], user[Users.name], user[Users.icon]).also { println(it) }

//        return User(UUID.randomUUID(), "Test", "icon")
    }
}
